package com.strumsoft.commons.jesque.executor;

import net.greghaines.jesque.worker.Worker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.strumsoft.commons.jesque.WorkerExecutor;

/**
 * SimpleThreadBasedWorkerExecutor which runs each worker in a separate thread.
 * 
 * @author "Animesh Kumar <animesh@strumsoft.com>"
 */
public class SimpleThreadBasedWorkerExecutor implements WorkerExecutor {
    /** The Constant LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(SimpleThreadBasedWorkerExecutor.class);

    /* (non-Javadoc)
     * @see com.strumsoft.commons.jesque.WorkerExecutor#execute(net.greghaines.jesque.worker.Worker)
     */
    @Override
    public void execute(Worker worker) {
        LOG.info("Executing worker={}", worker);
        final Thread workerThread = new Thread(worker);
        workerThread.setName(worker.getName());
        workerThread.start();
    }

}
