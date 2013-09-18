package com.strumsoft.commons.jesque;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import net.greghaines.jesque.Job;
import net.greghaines.jesque.worker.WorkerEvent;
import net.greghaines.jesque.worker.WorkerListener;

// TODO: Auto-generated Javadoc
/**
 * Worker Annotation. Must be applied on a Runnable class.
 * 
 * @author "Animesh Kumar <animesh@strumsoft.com>"
 * 
 */
@Target({ ElementType.TYPE })
@Retention(RUNTIME)
@Inherited
public @interface Worker {

    /**
     * Name of the job
     *
     * @return the string
     */
    String job();

    /**
     * Queue names that this worker will listen to.
     * 
     * @return the string[]
     */
    String[] queues();

    /**
     * Is this worker enabled?.
     *
     * @return true, if successful
     */
    boolean enabled() default true;

    /**
     * Number of instances of this worker.
     *
     * @return the int
     */
    int count() default 1;

    /**
     * WorkerEvents to listen to.
     *
     * @return the worker event[]
     */
    WorkerEvent[] events() default {};

    /**
     * Listener class that listens to worker events.
     * 
     * Note: This is Guice managed Listener and must be defined appropriately.
     * 
     * @return the class<? extends worker listener>
     */
    Class<? extends WorkerListener> listener() default WorkerListener.class;

    /**
     * The listener interface for receiving defaultWorker events. The class that is interested in processing a
     * defaultWorker event implements this interface, and the object created with that class is registered
     * with a component using the component's <code>addDefaultWorkerListener<code> method. When
     * the defaultWorker event occurs, that object's appropriate
     * method is invoked.
     * 
     * @see DefaultWorkerEvent
     */
    static final class DefaultWorkerListener implements WorkerListener {

        /*
         * (non-Javadoc)
         * 
         * @see net.greghaines.jesque.worker.WorkerListener#onEvent(net.greghaines.jesque.worker.WorkerEvent,
         * net.greghaines.jesque.worker.Worker, java.lang.String, net.greghaines.jesque.Job, java.lang.Object,
         * java.lang.Object, java.lang.Exception)
         */
        @Override
        public void onEvent(WorkerEvent event, net.greghaines.jesque.worker.Worker worker, String queue,
                Job job, Object runner, Object result, Exception ex) {
            // Do Nothing
        }
    }
}