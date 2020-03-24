package com.kagaya.kyaputen.server.grpc.service;

import com.kagaya.kyaputen.core.service.ExecutionService;
import com.kagaya.kyaputen.grpc.TaskServiceGrpc;
import com.kagaya.kyaputen.grpc.TaskServicePb;
import com.kagaya.kyaputen.proto.TaskPb;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class TaskServiceImpl extends TaskServiceGrpc.TaskServiceImplBase {

    public static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

    private final ExecutionService executionService;

    @Inject
    public TaskServiceImpl(ExecutionService executionService) {
        this.executionService = executionService;
    }

    @Override
    public void poll(TaskServicePb.PollRequest request, StreamObserver<TaskServicePb.PollResponse> responseObserver) {
        System.out.println(("Request Task Type: ") + request.getTaskType());
        System.out.println("Request Domain: " + request.getDomain());
        System.out.println(("Request Worker Id: ") + request.getWorkerId());

        TaskPb.Task task = TaskPb.Task.newBuilder().setStartTime(System.currentTimeMillis()/1000L).build();
        TaskServicePb.PollResponse response = TaskServicePb.PollResponse.newBuilder().setTask(task).build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
