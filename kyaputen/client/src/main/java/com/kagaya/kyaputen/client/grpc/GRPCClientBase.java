package com.kagaya.kyaputen.client.grpc;

import com.kagaya.kyaputen.grpc.ProtoMapper;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public abstract class GRPCClientBase {

    private static Logger logger = LoggerFactory.getLogger(GRPCClientBase.class);

    protected static ProtoMapper protoMapper = new ProtoMapper();

    protected final ManagedChannel channel;

    protected int awaitTerminationTime = 5;

    public GRPCClientBase(String address, int port) {
        this(ManagedChannelBuilder.forAddress(address, port).usePlaintext(true));
    }

    public GRPCClientBase(ManagedChannelBuilder<?> builder) {
        channel = builder.build();
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(awaitTerminationTime, TimeUnit.SECONDS);
    }

}
