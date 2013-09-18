package com.strumsoft.commons.jesque.example;

import net.greghaines.jesque.Config;
import net.greghaines.jesque.ConfigBuilder;
import net.greghaines.jesque.client.Client;
import net.greghaines.jesque.client.ClientImpl;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.strumsoft.commons.jesque.JesqueModule;
import com.strumsoft.commons.jesque.WorkerExecutor;
import com.strumsoft.commons.jesque.executor.SimpleThreadBasedWorkerExecutor;

public class ExampleModule extends AbstractModule {

    @Override
    protected void configure() {
        // Jesque Guice
        install(new JesqueModule());

        // Jesque Client
        Config config = new ConfigBuilder().withHost("localhost").withPort(6379).withDatabase(0).build();
        bind(Config.class).toInstance(config);
        bind(Client.class).toInstance(new ClientImpl(config));

        // Worker
        bind(ExampleWorker.class).asEagerSingleton(); // Must be singleton
        // WorkerEventListener
        bind(EchoListener.class).in(Scopes.SINGLETON);
        // Executor
        bind(WorkerExecutor.class).to(SimpleThreadBasedWorkerExecutor.class);
        // Service (will be injected into workers)
        bind(ExampleService.class).asEagerSingleton();
    }

}
