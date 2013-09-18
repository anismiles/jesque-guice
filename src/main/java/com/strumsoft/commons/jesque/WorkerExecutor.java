package com.strumsoft.commons.jesque;

import net.greghaines.jesque.worker.Worker;

/**
 * WorkerExecutor interface
 * 
 * @author "Animesh Kumar <animesh@strumsoft.com>"
 */
public interface WorkerExecutor {

    /**
     * Execute worker
     *
     * @param worker the worker
     */
    public void execute(Worker worker);
}
