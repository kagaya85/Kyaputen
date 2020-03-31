package com.kagaya.kyaputen.client.runner;

import com.google.common.base.Stopwatch;
import com.kagaya.kyaputen.client.entity.TaskClient;
import com.kagaya.kyaputen.client.worker.Worker;
import com.kagaya.kyaputen.common.metadata.tasks.Task;
import com.kagaya.kyaputen.common.metadata.tasks.TaskResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TaskPollExecutor {

    private static final Logger logger = LoggerFactory.getLogger(TaskPollExecutor.class);

//    private EurekaClient eurekaClient;
    private TaskClient taskClient;
    private int updateRetryCount;
    private ExecutorService executorService;

    public TaskPollExecutor(TaskClient taskClient, int updateRetryCount, String workerPrefixName) {

//        this.eurekaClient = eurekaClient;
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

    public void pollAndExecute(Worker worker) {

    }

    public void executeTask(Worker worker, Task task) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        TaskResult result = null;

    }

}
