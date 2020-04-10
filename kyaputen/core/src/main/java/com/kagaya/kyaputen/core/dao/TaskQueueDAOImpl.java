package com.kagaya.kyaputen.core.dao;

import com.kagaya.kyaputen.common.metadata.tasks.Task;

import java.util.*;

public class TaskQueueDAOImpl implements QueueDAO<Task> {

    private static Map<String, Queue<Task>> taskQueueMap = new HashMap<>();

    @Override
    public void push(String queueName, Task task) {
        Queue<Task> queue = taskQueueMap.get(queueName);

        if (queue == null) {
            taskQueueMap.put(queueName, new LinkedList<>());
        }

        queue.add(task);
    }

    @Override
    public Task get(String queueName) {
        Queue<Task> queue = taskQueueMap.get(queueName);

        if (queue != null)
            return queue.poll();
        else
            return null;
    }

    @Override
    public Task peek(String queueName) {
        Queue<Task> queue = taskQueueMap.get(queueName);

        if (queue != null)
            return queue.peek().copy();
        else
            return null;
    }

    @Override
    public void remove(String queueName, String id) {
        Queue<Task> queue = taskQueueMap.get(queueName);

        queue.removeIf(task -> task.getTaskId().equals(id));
    }

    @Override
    public int getSize(String queueName) {
        Queue<Task> queue = taskQueueMap.get(queueName);

        return queue.size();
    }

    @Override
    public boolean isEmpty(String queueName) {
        Queue<Task> queue = taskQueueMap.get(queueName);

        return queue.isEmpty();
    }

}
