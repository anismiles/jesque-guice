package com.strumsoft.commons.jesque.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.greghaines.jesque.worker.Worker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.strumsoft.commons.jesque.WorkerExecutor;

/**
 * CachedThreadPoolBasedWorkerExecutor that submits each workers to an ExecutorService.
 *
 * @author "Animesh Kumar <animesh@strumsoft.com>"
 */
public class CachedThreadPoolBasedWorkerExecutor implements WorkerExecutor {
    /** The Constant LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(CachedThreadPoolBasedWorkerExecutor.class);

    /** The executor. */
    private final ExecutorService executor;

    /**
     * Instantiates a new thread pool based worker executor.
     */
    public CachedThreadPoolBasedWorkerExecutor() {
        this.executor = Executors.newCachedThreadPool();
    }

    /* (non-Javadoc)
     * @see com.strumsoft.commons.jesque.WorkerExecutor#execute(net.greghaines.jesque.worker.Worker)
     */
    @Override
    public void execute(Worker worker) {
        LOG.info("Submitting worker={} to executor", worker);
        executor.submit(worker);
    }
    
    // @preDestroy
    public void preDestroy() {
        if (null != executor) {
            executor.shutdown();
        }
    }

}
