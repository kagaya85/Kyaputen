package com.kagaya.kyaputen.server;

import com.kagaya.kyaputen.server.grpc.GRPCServer;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import com.kagaya.kyaputen.grpc.TaskServiceGrpc;
import com.kagaya.kyaputen.grpc.TaskServicePb.PollRequest;
import com.kagaya.kyaputen.grpc.TaskServicePb.PollResponse;
import com.kagaya.kyaputen.proto.TaskPb.Task;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;

public class KyaputenServer {

    private static final Logger logger = LoggerFactory.getLogger(KyaputenServer.class);

    private final GRPCServer grpcServer;

    public KyaputenServer(int port) {

        logger.info("gRPC server bind at port " + port);

        grpcServer = new GRPCServer(port);
    }

    private class TaskServiceImpl extends TaskServiceGrpc.TaskServiceImplBase {

        @Override
        public void poll(PollRequest request, StreamObserver<PollResponse> responseObserver) {
            System.out.println(("Request Task Type: ") + request.getTaskType());
            System.out.println("Request Domain: " + request.getDomain());
            System.out.println(("Request Worker Id: ") + request.getWorkerId());

            Task task = Task.newBuilder().setStartTime(System.currentTimeMillis()/1000L).build();
            PollResponse response = PollResponse.newBuilder().setTask(task).build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }

}
