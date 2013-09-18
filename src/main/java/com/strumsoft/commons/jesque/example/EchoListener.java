package com.strumsoft.commons.jesque.example;

import net.greghaines.jesque.Job;
import net.greghaines.jesque.worker.Worker;
import net.greghaines.jesque.worker.WorkerEvent;
import net.greghaines.jesque.worker.WorkerListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EchoListener implements WorkerListener {
    // LOG
    private static final Logger LOG = LoggerFactory.getLogger(EchoListener.class);

    @Override
    public void onEvent(WorkerEvent event, Worker worker, String queue, Job job, Object runner,
            Object result, Exception ex) {
        LOG.info("onEvent ==>> queue={}, event={}", new Object[] { queue, event });
    }

}
