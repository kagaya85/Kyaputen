package com.kagaya.kyaputen.server.module;

import com.google.inject.AbstractModule;

public class ServerModule extends AbstractModule {

    @Override
    protected void configure() {

        install(new GRPCModule());
        install(new CoreModule());

    }
}
