package com.kagaya.kyaputen.client;

import com.kagaya.kyaputen.grpc.TaskServiceGrpc;
import com.kagaya.kyaputen.grpc.TaskServicePb.PollResponse;
import com.kagaya.kyaputen.grpc.TaskServicePb.PollRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

import java.util.concurrent.TimeUnit;

public class KyaputenClient {
    private final ManagedChannel channel;
    private final TaskServiceGrpc.TaskServiceBlockingStub blockingStub;
    private String host;
    private int port;
    private int awaitTerminationTime = 5;

    private String taskType;
    private String workerId;
    private String domain;

    public KyaputenClient() {
        channel = ManagedChannelBuilder.forAddress(this.host, this.port)
                .usePlaintext(true)
                .build();

        blockingStub = TaskServiceGrpc.newBlockingStub(channel);
    }

    public KyaputenClient(String host, int port, String taskType, String workerId, String domain) {
        this.host = host;
        this.port = port;
        
        this.taskType = taskType;
        this.workerId = workerId;
        this.domain = domain;
        
        channel = ManagedChannelBuilder.forAddress(this.host, this.port)
                .usePlaintext(true)
                .build();

        blockingStub = TaskServiceGrpc.newBlockingStub(channel);
    }

    public void shutDown() throws InterruptedException {
        channel.shutdown().awaitTermination(awaitTerminationTime, TimeUnit.SECONDS);
    }

    public void poll() {
        PollRequest request = PollRequest.newBuilder()
                .setTaskType(taskType)
                .setWorkerId(workerId)
                .setDomain(domain)
                .build();

        PollResponse response = blockingStub.poll(request);
        System.out.println("Get Time form host: " + response.getTask().getStartTime());
    }

}
