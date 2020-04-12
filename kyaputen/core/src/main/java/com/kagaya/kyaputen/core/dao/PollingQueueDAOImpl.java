package com.kagaya.kyaputen.core.dao;

import com.kagaya.kyaputen.common.metadata.tasks.Task;
import com.kagaya.kyaputen.common.metadata.tasks.TaskDefinition;
import com.kagaya.kyaputen.core.events.Message;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class PollingQueueDAOImpl implements QueueDAO<Message> {

    private static Map<String, List<Message>> taskQueueMap = new HashMap<>();

    @Override
    public void push(String queueName, Message task) {
        List<Message> queue = taskQueueMap.get(queueName);

        if (queue == null) {
            taskQueueMap.put(queueName, new LinkedList<>());
        }

        queue.add(task);
    }

    @Override
    public Message pop(String queueName) {
        List<Message> queue = taskQueueMap.get(queueName);

        if (queue != null) {
            Message message = queue.get(0);
            queue.remove(0);
            return message;
        }
        else
            return null;
    }

    @Override
    public Message pop(String queueName, String id) {
        List<Message> queue = taskQueueMap.get(queueName);

        if (queue != null) {
            for (int i = 0; i < queue.size(); i++) {
                Message message = queue.get(i);
                if (message.getId().equals(id)) {
                    queue.remove(i);
                    return message;
                }
            }
        }

        return null;
    }

    @Override
    public Message peek(String queueName) {
        List<Message> queue = taskQueueMap.get(queueName);

        if (queue != null)
            return queue.get(0);
        else
            return null;
    }

    @Override
    public Message peek(String queueName, String id) {
        List<Message> queue = taskQueueMap.get(queueName);

        if (queue != null) {
            for (int i = 0; i < queue.size(); i++) {
                Message message = queue.get(i);
                if (message.getId().equals(id)) {
                    return message;
                }
            }
        }

        return null;
    }

    @Override
    public void remove(String queueName, String id) {
        List<Message> queue = taskQueueMap.get(queueName);

        queue.removeIf(message -> message.getId().equals(id));
    }

    @Override
    public int getSize(String queueName) {
        List<Message> queue = taskQueueMap.get(queueName);

        return queue.size();
    }

    @Override
    public boolean isEmpty(String queueName) {
        List<Message> queue = taskQueueMap.get(queueName);

        return queue.isEmpty();
    }
}
