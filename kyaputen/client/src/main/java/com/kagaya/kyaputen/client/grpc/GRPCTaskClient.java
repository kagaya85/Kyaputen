package com.kagaya.kyaputen.client.grpc;

import com.kagaya.kyaputen.common.metadata.tasks.Task;
import com.kagaya.kyaputen.grpc.TaskServiceGrpc;
import com.kagaya.kyaputen.grpc.TaskServicePb;

public class GRPCTaskClient extends GRPCClientBase {

    private TaskServiceGrpc.TaskServiceBlockingStub blockingStub;

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

        return protoMapper.fromProto(response.getTask());
    }

}
