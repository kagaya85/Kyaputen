package com.kagaya.kyaputen.client.runner;

import com.google.common.base.Stopwatch;
import com.kagaya.kyaputen.client.config.KyaputenClientConfig;
import com.kagaya.kyaputen.client.entity.TaskClient;
import com.kagaya.kyaputen.client.worker.Worker;
import com.kagaya.kyaputen.common.metadata.tasks.Task;
import com.kagaya.kyaputen.common.metadata.tasks.Task.Status;
import com.kagaya.kyaputen.common.metadata.tasks.TaskResult;
import com.netflix.discovery.EurekaClient;
import com.netflix.appinfo.InstanceInfo.InstanceStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.concurrent.*;

public class TaskPollExecutor {

    private static final Logger logger = LoggerFactory.getLogger(TaskPollExecutor.class);

    private EurekaClient eurekaClient;
    private TaskClient taskClient;
    private int updateRetryCount;
    private ExecutorService executorService;

    public TaskPollExecutor(EurekaClient eurekaClient, TaskClient taskClient, int updateRetryCount, String workerPrefixName) {

        this.eurekaClient = eurekaClient;
        this.taskClient = taskClient;
        this.updateRetryCount = updateRetryCount;

        logger.info("Initialized TaskPollExecutor");

        this.executorService = Executors.newSingleThreadExecutor();

    }

    public void shutdownExecutorService(ExecutorService executorService) {
        executorService.shutdown();

        try {
            if (executorService.awaitTermination(10, TimeUnit.SECONDS)) {
                logger.debug("TaskExecutor completed, shutting down");
            } else {
                logger.warn("TaskExecutor forcing shutdown");
                executorService.shutdownNow();
            }
        } catch (Exception e) {
            logger.warn("TaskExecutor shutdown interrupted: " + e.getMessage());
            executorService.shutdownNow();
        }
    }

    public void shutdown() {
        shutdownExecutorService(executorService);
    }

    public void pollAndExecuteTask(Worker worker) {
        if (eurekaClient != null && !eurekaClient.getInstanceRemoteStatus().equals(InstanceStatus.UP)) {
            logger.debug("Service instance is NOT UP in discovery - will not poll");
            return;
        }

        Task task;

        try {
            String taskType = worker.getTaskDefName();
            String domain = KyaputenClientConfig.getDomain();

            logger.debug("Polling task of type: {} in domain: {}", taskType, domain);

            task = taskClient.pollTask(taskType, worker.getIdentity(), domain);
            if (Objects.nonNull(task) && task.getTaskId() != null) {
                logger.debug("Get Task from server, TaskType: {}, in domain: {}, from worker: {}", taskType, domain, worker.getIdentity());

                // 异步执行Task
                CompletableFuture<Task> taskCompletableFuture = CompletableFuture.supplyAsync(() ->
                        executeTask(worker, task), executorService);

                taskCompletableFuture.whenComplete(this::finalizeTask);
            }
        } catch ( Exception e) {
            logger.error("Error happend when task polling", e);
        }
    }

    public Task executeTask(Worker worker, Task task) {
        logger.debug("Executing task: {} of type: {} in worker: {} at {}", task.getTaskId(), task.getTaskDefName(),
                worker.getClass().getSimpleName(), worker.getIdentity());

        Stopwatch stopwatch = Stopwatch.createStarted();
        TaskResult result = null;

        try {
            result = worker.execute(task);
            result.setWorkflowInstanceId(task.getWorkflowInstanceId());
            result.setTaskId(task.getTaskId());
            result.setWorkerId(worker.getIdentity());
        } catch ( Exception e) {
            task.setStatus(Status.FAILED);
            result = new TaskResult(task);
            logger.error("Error happend when execute task", e);
        } finally {
            stopwatch.stop();
        }

        logger.debug("Task: {} executed by worker: {} at {} with status: {}", task.getTaskId(),
                worker.getClass().getSimpleName(), worker.getIdentity(), result.getStatus());

        updateTask(updateRetryCount, task, result, worker);
        return task;
    }

    private void updateTask(int count, Task task, TaskResult result, Worker worker) {
        try {
            taskClient.updateTask(result);
        } catch (Exception e) {
            logger.error(String.format("Error happend when update task: %s, in worker: %s, with result: %s",
                    task.getTaskDefName(), worker.getIdentity(), result.toString()), e);
        }
    }

    private void finalizeTask(Task task, Throwable throwable) {
        if (throwable != null) {
            logger.error("Error processing task: {} of type: {}", task.getTaskId(), task.getTaskType(), throwable);
        } else {
            logger.debug("Task:{} of type:{} finished processing with status:{}", task.getTaskId(),
                    task.getTaskDefName(), task.getStatus());
        }
    }

}
