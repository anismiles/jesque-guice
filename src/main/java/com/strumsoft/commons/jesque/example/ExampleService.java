package com.strumsoft.commons.jesque.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExampleService {
    /** The Constant LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(ExampleService.class);

    public void serve() {
        LOG.info("Heya! I am not here to serve, just to show you how injection works.");
    }
}
