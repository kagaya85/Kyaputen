package com.kagaya.kyaputen.core.dao;

import com.kagaya.kyaputen.common.metadata.tasks.TaskDefinition;

import java.util.*;

/**
 * @description 工作流任务定义存储
 */
public class TaskDefQueueDAOImpl implements QueueDAO<TaskDefinition> {

    private static Map<String, List<TaskDefinition>> taskDefQueueMap = new HashMap<>();

    @Override
    public void push(String queueName, TaskDefinition task) {
        List<TaskDefinition> queue = taskDefQueueMap.get(queueName);

        if (queue == null) {
            taskDefQueueMap.put(queueName, new LinkedList<>());
        }
        else
            queue.add(task);
    }

    @Override
    public TaskDefinition pop(String queueName) {
        List<TaskDefinition> queue = taskDefQueueMap.get(queueName);

        if (queue != null) {
            TaskDefinition taskDef = queue.get(0);
            queue.remove(0);
            return taskDef;
        }
        else
            return null;
    }

    @Override
    public TaskDefinition pop(String queueName, String id) {
        List<TaskDefinition> queue = taskDefQueueMap.get(queueName);

        if (queue != null) {
            for (int i = 0; i < queue.size(); i++) {
                TaskDefinition taskDef = queue.get(i);
                if (taskDef.getTaskDefName().equals(id)) {
                    queue.remove(i);
                    return taskDef;
                }
            }
        }

        return null;
    }

    @Override
    public TaskDefinition peek(String queueName) {
        List<TaskDefinition> queue = taskDefQueueMap.get(queueName);

        if (queue != null)
            return queue.get(0);
        else
            return null;
    }

    @Override
    public TaskDefinition peek(String queueName, String id) {
        List<TaskDefinition> queue = taskDefQueueMap.get(queueName);

        if (queue != null) {
            for (int i = 0; i < queue.size(); i++) {
                TaskDefinition taskDef = queue.get(i);
                if (taskDef.getTaskDefName() == id)
                    return taskDef;
            }
        }

        return null;
    }

    @Override
    public void remove(String queueName, String id) {
        List<TaskDefinition> queue = taskDefQueueMap.get(queueName);

        queue.removeIf(task -> task.getTaskDefName().equals(id));
    }

    @Override
    public int getSize(String queueName) {
        List<TaskDefinition> queue = taskDefQueueMap.get(queueName);

        return queue.size();
    }

    @Override
    public boolean isEmpty(String queueName) {
        List<TaskDefinition> queue = taskDefQueueMap.get(queueName);

        return queue.isEmpty();
    }
}
