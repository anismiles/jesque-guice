package com.strumsoft.commons.jesque.example;

import javax.inject.Inject;

import net.greghaines.jesque.worker.WorkerEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.strumsoft.commons.jesque.Worker;

@Worker(job = "ExampleJob",                 // job name
        queues = { "EXP_QUEUE" },           // queue names
        enabled = true,                     // enabled
        count = 1,                          // 1 instance of this worker running
        events = { WorkerEvent.JOB_SUCCESS, WorkerEvent.WORKER_START }, // Events to listen to
        listener = EchoListener.class      // WorkerEventListener
)
public class ExampleWorker implements Runnable {
    // LOG
    private static final Logger LOG = LoggerFactory.getLogger(ExampleWorker.class);

    // Note: Only field level injection would work!
    @Inject
    ExampleService service;

    String arg1;
    String arg2;

    // Must keep an empty constructor for Guice to discover this
    public ExampleWorker() {
    }

    public ExampleWorker(String arg1, String arg2) {
        this.arg1 = arg1;
        this.arg2 = arg2;
    }

    @Override
    public void run() {
        LOG.info("Running worker={}, with arg1={}, arg2={}", new Object[] { getClass(), arg1, arg2 });
        // calling service
        service.serve();
    }

}
