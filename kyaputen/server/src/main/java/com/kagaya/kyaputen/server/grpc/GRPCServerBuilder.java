package com.kagaya.kyaputen.server.grpc;

import com.google.common.collect.ImmutableList;
import com.kagaya.kyaputen.grpc.TaskServiceGrpc;
import com.kagaya.kyaputen.server.grpc.service.GRPCServerConfiguration;
import io.grpc.BindableService;

import javax.inject.Inject;

public class GRPCServerBuilder {

    private final BindableService taskServiceImpl;

    @Inject
    public GRPCServerBuilder(TaskServiceGrpc.TaskServiceImplBase taskServiceImpl) {
        this.taskServiceImpl = taskServiceImpl;
    }

    public GRPCServer build() {
        ImmutableList.Builder<BindableService> services =
                ImmutableList.<BindableService>builder().add(taskServiceImpl);

        return new GRPCServer(
                new GRPCServerConfiguration().getPort(),
                services.build().toArray(new BindableService[]{})
        );
    }

}
