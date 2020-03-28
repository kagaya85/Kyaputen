package com.kagaya.kyaputen.client.grpc;

import com.kagaya.kyaputen.client.entity.TaskClient;
import com.kagaya.kyaputen.common.metadata.tasks.Task;
import com.kagaya.kyaputen.grpc.TaskServiceGrpc;
import com.kagaya.kyaputen.grpc.TaskServicePb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GRPCTaskClient extends GRPCClientBase {

    private TaskServiceGrpc.TaskServiceBlockingStub blockingStub;

    public static final Logger logger = LoggerFactory.getLogger(TaskClient.class);

    public GRPCTaskClient(String address, int port) {
        super(address, port);
        blockingStub = TaskServiceGrpc.newBlockingStub(channel);
    }

    public Task pollTask(String taskType, String workerId, String domain) {
        // 检查合法性

        TaskServicePb.PollRequest request = TaskServicePb.PollRequest.newBuilder()
            .setTaskType(taskType)
            .setWorkerId(workerId)
            .setDomain(domain)
            .build();

        TaskServicePb.PollResponse response = blockingStub.poll(request);

        logger.debug("GRPCTaskClient pollTask get response, task status: " + response.getTask().getStatus());

        return protoMapper.fromProto(response.getTask());
    }

}
