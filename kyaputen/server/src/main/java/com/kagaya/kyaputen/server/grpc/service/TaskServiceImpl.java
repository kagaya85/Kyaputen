package com.kagaya.kyaputen.server.grpc.service;

import com.kagaya.kyaputen.common.metadata.tasks.Task;
import com.kagaya.kyaputen.core.service.ExecutionService;
import com.kagaya.kyaputen.grpc.ProtoMapper;
import com.kagaya.kyaputen.grpc.TaskServiceGrpc;
import com.kagaya.kyaputen.grpc.TaskServicePb;
import com.kagaya.kyaputen.proto.TaskPb;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.List;

public class TaskServiceImpl extends TaskServiceGrpc.TaskServiceImplBase {

    public static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);
    private static final ProtoMapper protoMapper = new ProtoMapper();


    private static final int MAX_TASK_COUNT = 100;
    private static final int POLL_TIMEOUT_MS = 100;
    private static final int MAX_POLL_TIMEOUT_MS = 5000;

    private final ExecutionService executionService;

    @Inject
    public TaskServiceImpl(ExecutionService executionService) {
        this.executionService = executionService;
    }

    @Override
    public void poll(TaskServicePb.PollRequest request, StreamObserver<TaskServicePb.PollResponse> response) {

        try {
            // 调用 {@link ExecutionService} 中的方法
            logger.debug(String.format("Get request, TaskType: %s, WorkerId: %s, Domain: %s",
                    request.getTaskType(), request.getWorkerId(), request.getDomain()));
            // 查询可以分配的任务列表
            List<Task> tasks = executionService.getPollTaskList(request.getTaskType(), request.getWorkerId(),
                    request.getDomain(), 1, POLL_TIMEOUT_MS);

            if (!tasks.isEmpty()) {
                TaskPb.Task t = protoMapper.toProto(tasks.get(0));
                logger.debug("TaskPb Task set status: " + t.getStatus());
                response.onNext(TaskServicePb.PollResponse.newBuilder()
                        .setTask(t)
                        .build()
                );
            }
            response.onCompleted();
        } catch (Exception e) {
            logger.error("TaskServiceImpl poll error: " + e.getMessage());
            e.printStackTrace(System.err);
        }
    }
}
