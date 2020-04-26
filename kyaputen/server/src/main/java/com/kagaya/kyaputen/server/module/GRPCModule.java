package com.kagaya.kyaputen.server.module;

import com.google.inject.AbstractModule;
import com.kagaya.kyaputen.grpc.TaskServiceGrpc;
import com.kagaya.kyaputen.server.grpc.GRPCServerBuilder;
import com.kagaya.kyaputen.server.grpc.service.TaskServiceImpl;

public class GRPCModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(TaskServiceGrpc.TaskServiceImplBase.class).to(TaskServiceImpl.class);
        bind(GRPCServerBuilder.class);
    }
}
