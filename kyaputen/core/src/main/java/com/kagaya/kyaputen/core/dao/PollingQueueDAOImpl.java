package com.kagaya.kyaputen.core.dao;

import com.kagaya.kyaputen.common.metadata.tasks.Task;
import com.kagaya.kyaputen.core.events.Message;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class PollingQueueDAOImpl implements QueueDAO<Message> {

    private static Map<String, Queue<Message>> taskQueueMap = new HashMap<>();

    @Override
    public void push(String queueName, Message task) {
        Queue<Message> queue = taskQueueMap.get(queueName);

        if (queue == null) {
            taskQueueMap.put(queueName, new LinkedList<>());
        }

        queue.add(task);
    }

    @Override
    public Message get(String queueName) {
        Queue<Message> queue = taskQueueMap.get(queueName);

        if (queue != null)
            return queue.poll();
        else
            return null;
    }

    @Override
    public Message peek(String queueName) {
        Queue<Message> queue = taskQueueMap.get(queueName);

        if (queue != null)
            return queue.peek();
        else
            return null;
    }

    @Override
    public void remove(String queueName, String id) {
        Queue<Message> queue = taskQueueMap.get(queueName);

        queue.removeIf(message -> message.getId().equals(id));
    }

    @Override
    public int getSize(String queueName) {
        Queue<Message> queue = taskQueueMap.get(queueName);

        return queue.size();
    }

    @Override
    public boolean isEmpty(String queueName) {
        Queue<Message> queue = taskQueueMap.get(queueName);

        return queue.isEmpty();
    }
}
