package com.kagaya.kyaputen.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface Lifecycle {

    Logger logger = LoggerFactory.getLogger(Lifecycle.class);

    default void start() throws Exception {
        registerShutdownHook();
    }

    void stop() throws Exception;

    default void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                stop();
            } catch (Exception e) {
                logger.error("Error when trying to shutdown a lifecycle component: " + this.getClass().getName(), e);
            }
        }));
    }
}
