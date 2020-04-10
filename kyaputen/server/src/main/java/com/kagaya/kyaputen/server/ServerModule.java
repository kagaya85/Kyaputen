package com.kagaya.kyaputen.server;

import com.google.inject.AbstractModule;
import com.kagaya.kyaputen.server.grpc.GRPCModule;

public class ServerModule extends AbstractModule {

    @Override
    protected void configure() {

        install(new GRPCModule());
        install(new DAOModule());

    }
}
