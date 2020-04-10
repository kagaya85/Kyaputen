package com.kagaya.kyaputen.core.dao;

import com.kagaya.kyaputen.common.metadata.tasks.TaskDefinition;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * @description 工作流任务定义存储
 */
public class TaskDefQueueDAOImpl implements QueueDAO<TaskDefinition> {

    private static Map<String, Queue<TaskDefinition>> taskDefQueueMap = new HashMap<>();

    @Override
    public void push(String queueName, TaskDefinition task) {
        Queue<TaskDefinition> queue = taskDefQueueMap.get(queueName);

        if (queue == null) {
            taskDefQueueMap.put(queueName, new LinkedList<>());
        }

        queue.add(task);
    }

    @Override
    public TaskDefinition get(String queueName) {
        Queue<TaskDefinition> queue = taskDefQueueMap.get(queueName);

        if (queue != null)
            return queue.poll();
        else
            return null;
    }

    @Override
    public TaskDefinition peek(String queueName) {
        Queue<TaskDefinition> queue = taskDefQueueMap.get(queueName);

        if (queue != null)
            return queue.peek();
        else
            return null;
    }

    @Override
    public void remove(String queueName, String id) {
        Queue<TaskDefinition> queue = taskDefQueueMap.get(queueName);

        queue.removeIf(task -> task.getTaskDefName().equals(id));
    }

    @Override
    public int getSize(String queueName) {
        Queue<TaskDefinition> queue = taskDefQueueMap.get(queueName);

        return queue.size();
    }

    @Override
    public boolean isEmpty(String queueName) {
        Queue<TaskDefinition> queue = taskDefQueueMap.get(queueName);

        return queue.isEmpty();
    }
}
