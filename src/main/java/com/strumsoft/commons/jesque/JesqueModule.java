package com.strumsoft.commons.jesque;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import net.greghaines.jesque.worker.WorkerEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Provider;
import com.google.inject.TypeLiteral;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.google.inject.matcher.AbstractMatcher;
import com.google.inject.spi.InjectionListener;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;

/**
 * Loads/Discovers relevant classes
 * 
 * @author "Animesh Kumar <animesh@strumsoft.com>"
 */
public class JesqueModule extends AbstractModule {
    /** The Constant LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(JesqueModule.class);

    /*
     * (non-Javadoc)
     * 
     * @see com.google.inject.AbstractModule#configure()
     */
    @Override
    protected void configure() {
        // Worker factory
        install(new FactoryModuleBuilder().build(GuiceAwareWorkerFactory.class));
        // Worker discovery
        install(new WorkerDiscoveryModule());
    }

    /**
     * The Class WorkerDiscoveryModule.
     */
    static class WorkerDiscoveryModule extends AbstractModule {

        final Set<Class<? extends Runnable>> visitedWorkerRunnables = new HashSet<Class<? extends Runnable>>();

        /*
         * (non-Javadoc)
         * 
         * @see com.google.inject.AbstractModule#configure()
         */
        @Override
        protected void configure() {
            // Discover workers
            bindListener(new AbstractMatcher<TypeLiteral<?>>() {
                @Override
                public boolean matches(TypeLiteral<?> type) {
                    return type.getRawType().isAnnotationPresent(Worker.class);
                }
            }, new TypeListener() {
                @Override
                public <I> void hear(final TypeLiteral<I> type, final TypeEncounter<I> encounter) {

                    final Provider<GuiceAwareWorkerFactory> factoryProvider = encounter
                            .getProvider(GuiceAwareWorkerFactory.class);
                    final Provider<Injector> injectorProvider = encounter.getProvider(Injector.class);

                    encounter.register(new InjectionListener<I>() {
                        @Override
                        public void afterInjection(I injectee) {
                            // Implements Runnable?
                            if (!Runnable.class.isAssignableFrom(injectee.getClass())) {
                                LOG.warn("{} annotated with {} must implement java.lang.Runnable interface",
                                        injectee.getClass().getName(), Worker.class.getName());
                                return;
                            }

                            // Type cast to Runnable
                            final Runnable runnable = (Runnable) injectee;

                            // Already visited?
                            if (alreadyVisited(runnable)) {
                                return;
                            }

                            // Annotation
                            final Worker ann = injectee.getClass().getAnnotation(Worker.class);

                            // enabled?
                            if (!ann.enabled()) {
                                LOG.info("Worker={} is skipped", injectee.getClass());
                                return;
                            }

                            final String job = ann.job();
                            final String[] queues = ann.queues();
                            final WorkerEvent[] events = ann.events();

                            int count = ann.count();

                            LOG.info("Registering {} with Queues={} for {} time(s)", new Object[] {
                                    runnable.getClass().getName(), Arrays.asList(queues), count });

                            while (count > 0) {
                                GuiceAwareWorker worker = factoryProvider.get().create(job,
                                        runnable.getClass(), Arrays.asList(queues));
                                worker.addListener(injectorProvider.get().getInstance(ann.listener()), events);
                                worker.init();
                                count--;
                            }
                            
                            // Mark as visited 
                            markVisited(runnable);
                        }
                    });
                }
            });
        }

        boolean alreadyVisited(Runnable runnable) {
            return visitedWorkerRunnables.contains(runnable.getClass());
        }

        void markVisited(Runnable runnable) {
            visitedWorkerRunnables.add(runnable.getClass());
        }

    }

}
