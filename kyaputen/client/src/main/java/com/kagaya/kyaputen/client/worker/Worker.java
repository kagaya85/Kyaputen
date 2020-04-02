package com.kagaya.kyaputen.client.worker;

import com.kagaya.kyaputen.common.metadata.tasks.Task;
import com.kagaya.kyaputen.common.metadata.tasks.TaskResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.function.Function;

public interface Worker {

    String getTaskDefName();

    TaskResult execute(Task task);

    default String getIdentity() {
        String workerId;
        try {
            workerId = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            workerId = System.getenv("HOSTNAME");
        }
        LoggerHolder.logger.debug("Setting worker id to {}", workerId);
        return workerId;
    }

    static Worker create(String taskType, Function<Task, TaskResult> executor) {
        return new Worker() {

            @Override
            public String getTaskDefName() {
                return taskType;
            }

            @Override
            public TaskResult execute(Task task) {
                return executor.apply(task);
            }
        };
    }
}

final class LoggerHolder {
    static final Logger logger = LoggerFactory.getLogger(Worker.class);
}
