package com.kagaya.kyaputen.server;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.kagaya.kyaputen.server.grpc.GRPCServer;
import com.kagaya.kyaputen.server.grpc.GRPCServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KyaputenServer {

    private static final Logger logger = LoggerFactory.getLogger(KyaputenServer.class);

    private final GRPCServer grpcServer;
    private final GRPCServerBuilder grpcServerBuilder;

    public KyaputenServer(int port) {

        Injector injector = Guice.createInjector(new ServerModule());

        grpcServerBuilder = injector.getInstance(GRPCServerBuilder.class);

        grpcServer = grpcServerBuilder.build();
    }

    public void start() {
        try {
            grpcServer.start();
            grpcServer.blockUntilShutdown();
        }
        catch (Exception e) {
            logger.error("gRPC server error occured: " + e.getMessage());
            e.printStackTrace(System.err);
            System.exit(3);
        }
    }

//    private class TaskServiceImpl extends TaskServiceGrpc.TaskServiceImplBase {
//
//        @Override
//        public void poll(PollRequest request, StreamObserver<PollResponse> responseObserver) {
//            System.out.println(("Request Task Type: ") + request.getTaskType());
//            System.out.println("Request Domain: " + request.getDomain());
//            System.out.println(("Request Worker Id: ") + request.getWorkerId());
//
//            Task task = Task.newBuilder().setStartTime(System.currentTimeMillis()/1000L).build();
//            PollResponse response = PollResponse.newBuilder().setTask(task).build();
//
//            responseObserver.onNext(response);
//            responseObserver.onCompleted();
//        }
//    }

}
