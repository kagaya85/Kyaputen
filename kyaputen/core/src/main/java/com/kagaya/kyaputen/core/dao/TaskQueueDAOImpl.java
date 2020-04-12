package com.kagaya.kyaputen.core.dao;

import com.kagaya.kyaputen.common.metadata.tasks.Task;
import com.kagaya.kyaputen.core.events.Message;

import java.util.*;

public class TaskQueueDAOImpl implements QueueDAO<Task> {

    private String queueNamePrefix = "task-";

    private static Map<String, List<Task>> taskQueueMap = new HashMap<>();

    @Override
    public void push(String queueName, Task task) {
        queueName = queueNamePrefix + queueName;

        List<Task> queue = taskQueueMap.get(queueName);

        if (queue == null) {
            taskQueueMap.put(queueName, new LinkedList<>());
        }

        queue.add(task);
    }

    @Override
    public Task pop(String queueName) {
        queueName = queueNamePrefix + queueName;

        List<Task> queue = taskQueueMap.get(queueName);

        if (queue != null) {
            Task task = queue.get(0);
            queue.remove(0);
            return task.copy();
        }
        else
            return null;
    }

    @Override
    public Task pop(String queueName, String id) {
        queueName = queueNamePrefix + queueName;

        List<Task> queue = taskQueueMap.get(queueName);

        if (queue != null) {
            for (int i = 0; i < queue.size(); i++) {
                Task task = queue.get(i);
                if (task.getTaskId().equals(id)) {
                    queue.remove(i);
                    return task.copy();
                }
            }
        }

        return null;
    }

    @Override
    public Task get(String queueName) {
        queueName = queueNamePrefix + queueName;

        List<Task> queue = taskQueueMap.get(queueName);

        if (queue != null)
            return queue.get(0);
        else
            return null;
    }

    @Override
    public Task get(String queueName, String id) {
        queueName = queueNamePrefix + queueName;

        List<Task> queue = taskQueueMap.get(queueName);

        if (queue != null) {
            for (int i = 0; i < queue.size(); i++) {
                Task task = queue.get(i);
                if (task.getTaskId().equals(id)) {
                    return task;
                }
            }
        }

        return null;
    }

    @Override
    public void remove(String queueName, String id) {
        queueName = queueNamePrefix + queueName;

        List<Task> queue = taskQueueMap.get(queueName);

        queue.removeIf(task -> task.getTaskId().equals(id));
    }

    @Override
    public int getSize(String queueName) {
        queueName = queueNamePrefix + queueName;

        List<Task> queue = taskQueueMap.get(queueName);

        return queue.size();
    }

    @Override
    public boolean isEmpty(String queueName) {
        queueName = queueNamePrefix + queueName;

        List<Task> queue = taskQueueMap.get(queueName);

        return queue.isEmpty();
    }

}
