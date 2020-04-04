package com.kagaya.kyaputen.client.runner;

import com.google.common.base.Preconditions;
import com.kagaya.kyaputen.client.config.KyaputenClientConfig;
import com.kagaya.kyaputen.client.entity.TaskClient;
import com.kagaya.kyaputen.client.worker.Worker;
import com.netflix.discovery.EurekaClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @description Client执行类，负责配置客户端入口以及执行客户端轮询
 */
public class TaskRunner {

    private final EurekaClient eurekaClient;
    private final TaskClient taskClient;
    private Worker worker;
    private TaskPollExecutor taskPollExecutor;
    private ScheduledExecutorService scheduledExecutorService;
    private static final Logger logger = LoggerFactory.getLogger(TaskRunner.class);

    private String workerPrefixName;
    private int retrySleep;
    private int updateRetryCount;

    public static class Builder {

        private String workerPrefixName = "worker-";
        private int retrySleep = 500;
        private int updateRetryCount = 3;
        private EurekaClient eurekaClient;
        private TaskClient taskClient;
        private Worker worker;

        public Builder(TaskClient taskClient, Worker worker) {
            Preconditions.checkNotNull(taskClient, "Task client can not be null");
            Preconditions.checkNotNull(worker, "worker can not be null");

            this.taskClient = taskClient;
            this.worker = worker;
        }

        public Builder withworkerPrefixName(String workerPrefixName) {
            this.workerPrefixName = workerPrefixName;
            return this;
        }

        public Builder withretrySleep(int retrySleep) {
            this.retrySleep = retrySleep;
            return this;
        }

        public Builder withUpdateRetryCount(int updateRetryCount) {
            this.updateRetryCount = updateRetryCount;
            return this;
        }

        public Builder withEurekaClient(EurekaClient eurekaClient) {
            this.eurekaClient = eurekaClient;
            return this;
        }

        public TaskRunner build() {
            return new TaskRunner(this);
        }
    }

    private TaskRunner(Builder builder){
        this.eurekaClient = builder.eurekaClient;
        this.taskClient = builder.taskClient;
        this.worker = builder.worker;

        this.workerPrefixName = builder.workerPrefixName;
        this.retrySleep = builder.retrySleep;
        this.updateRetryCount = builder.updateRetryCount;

        logger.info("Initialized TaskRunner");
    }

    public int getRetrySleep() {
        return retrySleep;
    }

    public int getUpdateRetryCount() {
        return updateRetryCount;
    }

    public String getWorkerPrefixName() {
        return workerPrefixName;
    }

    public void start() {
        this.taskPollExecutor = new TaskPollExecutor(eurekaClient, taskClient, updateRetryCount, workerPrefixName);
        this.scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        logger.info("TaskRunner start poll and execute Task");
        this.scheduledExecutorService.scheduleWithFixedDelay(() -> taskPollExecutor.pollAndExecuteTask(worker),
                KyaputenClientConfig.getPollingInterval(), KyaputenClientConfig.getPollingInterval(), TimeUnit.MILLISECONDS);
    }

    public void shutdown() {
        logger.info("TaskRunner shutting down...");
        taskPollExecutor.shutdownExecutorService(scheduledExecutorService);
    }
}
