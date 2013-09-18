package com.strumsoft.commons.jesque;

import java.util.List;

/**
 * A factory for creating GuiceAwareWorker objects.
 * 
 * @author "Animesh Kumar <animesh@strumsoft.com>"
 */
public interface GuiceAwareWorkerFactory {


    /**
     * Creates GuiceAwareWorker object
     *
     * @param job the job
     * @param runnable the runnable
     * @param queue the queue
     * @return the guice aware worker
     */
    GuiceAwareWorker create(final String job, final Class<? extends Runnable> runnable, final List<String> queue);
}
