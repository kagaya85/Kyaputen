package com.kagaya.kyaputen.server;

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

public class KyaputenServer {
    private static Logger logger = LoggerFactory.getLogger(KyaputenServer.class);
    private int port;
    private Server server;

    public KyaputenServer(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        server = ServerBuilder.forPort(port)
                .addService(new TaskServiceImpl())
                .build()
                .start();
    }

    public void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    public void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
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
