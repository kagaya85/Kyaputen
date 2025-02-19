package com.kagaya.kyaputen.client.entity;

import com.kagaya.kyaputen.client.config.KyaputenClientConfig;
import com.kagaya.kyaputen.common.metadata.tasks.Task;
import com.kagaya.kyaputen.client.grpc.GRPCTaskClient;
import com.kagaya.kyaputen.common.metadata.tasks.TaskResult;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.List;

/**
 * @description: 客户端任务接口，实现任务轮训工具方法以及任务执行方法
 */

public class TaskClient extends ClientBase {
    private List taskList;
    private List pollDataList;
    private GRPCTaskClient grpcTaskClient;

    public static final Logger logger = LoggerFactory.getLogger(TaskClient.class);

    public TaskClient() {
        grpcTaskClient = new GRPCTaskClient(KyaputenClientConfig.getAddress(), KyaputenClientConfig.getPort());
    }

    public TaskClient(KyaputenClientConfig config) {
        super(config);
        grpcTaskClient = new GRPCTaskClient(config.getAddress(), config.getPort());
    }

    public Task pollTask(String taskType, String workId, String domain) {
        // 检查合法性

        return grpcTaskClient.pollTask(taskType, workId, domain);
    }

    public void updateTask(TaskResult taskResult) {
        grpcTaskClient.updateTask(taskResult);
    }

}
