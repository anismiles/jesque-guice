package com.strumsoft.commons.jesque;

import static net.greghaines.jesque.utils.JesqueUtils.entry;
import static net.greghaines.jesque.utils.JesqueUtils.map;

import java.util.List;

import javax.inject.Inject;

import net.greghaines.jesque.Config;
import net.greghaines.jesque.Job;
import net.greghaines.jesque.worker.WorkerImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Injector;
import com.google.inject.assistedinject.Assisted;

/**
 * The Class extends net.greghaines.jesque.worker.WorkerImpl and adds Guice Injection.
 * Note: Only field level injections are supported!
 * 
 * @author "Animesh Kumar <animesh@strumsoft.com>"
 * 
 */
public class GuiceAwareWorker extends WorkerImpl {
    /** The Constant LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(GuiceAwareWorker.class);

    /** The injector. */
    final Injector injector;
    
    /** The executor. */
    final WorkerExecutor executor;
    
    /** The runnable. */
    final Class<? extends Runnable> runnable;
    
    /** The queues. */
    final List<String> queues;


    /**
     * Instantiates a new guice aware worker.
     *
     * @param injector the injector
     * @param config the config
     * @param executor the executor
     * @param name the name
     * @param runnable the runnable
     * @param queues the queues
     */
    @SuppressWarnings("unchecked")
    @Inject
    public GuiceAwareWorker(final Injector injector, final Config config, final WorkerExecutor executor,
            @Assisted final String job, @Assisted final Class<? extends Runnable> runnable,
            @Assisted final List<String> queues) {
        super(config, queues, map(entry(job, runnable)));
        this.queues = queues;
        this.runnable = runnable;
        this.injector = injector;
        this.executor = executor;
    }

    /**
     * init.
     */
    public void init() {
        LOG.debug("Starting worker={}", getName());
        // execute
        executor.execute(this);
    }

    /**
     * Gets the runnable.
     *
     * @return the runnable
     */
    public Class<? extends Runnable> getRunnable() {
        return runnable;
    }

    /* (non-Javadoc)
     * @see net.greghaines.jesque.worker.WorkerImpl#getQueues()
     */
    public List<String> getQueues() {
        return queues;
    }

    /* (non-Javadoc)
     * @see net.greghaines.jesque.worker.WorkerImpl#execute(net.greghaines.jesque.Job, java.lang.String, java.lang.Object)
     */
    @Override
    protected Object execute(final Job job, final String curQueue, final Object instance) throws Exception {
        // perform injection
        LOG.debug("Injecting dependencies into worker instance = {}", instance);
        injector.injectMembers(instance);
        // delegate
        LOG.debug("Delegating to run worker instance = {}", instance);
        return super.execute(job, curQueue, instance);
    }
}
