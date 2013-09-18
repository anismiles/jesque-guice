package com.strumsoft.commons.jesque.example;

import net.greghaines.jesque.Job;
import net.greghaines.jesque.client.Client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Stage;

public class Main {
    /** The Constant LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    /**
     * @param args
     */
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(Stage.DEVELOPMENT, new Module[] { new ExampleModule() });

        Client client = (Client) injector.getInstance(Client.class);
        LOG.info("Publish jobs");

        client.enqueue("EXP_QUEUE", new Job("ExampleJob", "hello", "job1"));
        client.enqueue("EXP_QUEUE", new Job("ExampleJob", "hello", "job2"));
        client.enqueue("EXP_QUEUE", new Job("ExampleJob", "hello", "job3"));
        client.enqueue("EXP_QUEUE", new Job("ExampleJob", "hello", "job4"));
    }

}
