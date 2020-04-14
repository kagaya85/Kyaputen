package com.kagaya.kyaputen.core.dao;

import com.kagaya.kyaputen.core.events.TaskMessage;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class PollingQueueDAOImpl implements QueueDAO<TaskMessage> {

    private String queueNamePrefix = "PollingQueue-";

    private static Map<String, List<TaskMessage>> taskQueueMap = new HashMap<>();

    @Override
    public void push(String queueName, TaskMessage task) {
        queueName = queueNamePrefix + queueName;

        List<TaskMessage> queue = taskQueueMap.get(queueName);

        if (queue == null) {
            taskQueueMap.put(queueName, new LinkedList<>());
        }
        else
            queue.add(task);
    }

    @Override
    public TaskMessage pop(String queueName) {
        queueName = queueNamePrefix + queueName;

        List<TaskMessage> queue = taskQueueMap.get(queueName);

        if (queue != null) {
            TaskMessage taskMessage = queue.get(0);
            queue.remove(0);
            return taskMessage;
        }
        else
            return null;
    }

    @Override
    public TaskMessage pop(String queueName, String id) {
        queueName = queueNamePrefix + queueName;

        List<TaskMessage> queue = taskQueueMap.get(queueName);

        if (queue != null) {
            for (int i = 0; i < queue.size(); i++) {
                TaskMessage taskMessage = queue.get(i);
                if (taskMessage.getTaskId().equals(id)) {
                    queue.remove(i);
                    return taskMessage;
                }
            }
        }

        return null;
    }

    @Override
    public TaskMessage get(String queueName) {
        queueName = queueNamePrefix + queueName;

        List<TaskMessage> queue = taskQueueMap.get(queueName);

        if (queue != null)
            return queue.get(0);
        else
            return null;
    }

    @Override
    public TaskMessage get(String queueName, String id) {
        queueName = queueNamePrefix + queueName;

        List<TaskMessage> queue = taskQueueMap.get(queueName);

        if (queue != null) {
            for (TaskMessage taskMessage : queue) {
                if (taskMessage.getTaskId().equals(id)) {
                    return taskMessage;
                }
            }
        }

        return null;
    }

    @Override
    public void remove(String queueName, String id) {
        queueName = queueNamePrefix + queueName;

        List<TaskMessage> queue = taskQueueMap.get(queueName);

        queue.removeIf(taskMessage -> taskMessage.getTaskId().equals(id));
    }

    @Override
    public int getSize(String queueName) {
        queueName = queueNamePrefix + queueName;

        List<TaskMessage> queue = taskQueueMap.get(queueName);

        return queue.size();
    }

    @Override
    public boolean isEmpty(String queueName) {
        queueName = queueNamePrefix + queueName;

        List<TaskMessage> queue = taskQueueMap.get(queueName);

        return queue.isEmpty();
    }
}
