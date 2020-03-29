package com.kagaya.kyaputen.core.dao;

import com.kagaya.kyaputen.core.events.Message;

import java.util.*;

public class QueueDAOImpl implements QueueDAO {

    private Map<String, Queue<Message>> queueMap = new HashMap<>();

    @Override
    public void push(String queueName, String id) {
        push(queueName, id, 0);
    }

    @Override
    public void push(String queueName, List<Message> messages) {
        Queue<Message> queue = queueMap.get(queueName);

        queue.addAll(messages);
    }

    @Override
    public void push(String queueName, String id, int priority) {
        Queue<Message> queue = queueMap.get(queueName);

        queue.add(new Message(id, priority));
    }

    @Override
    public List<String> pop(String queueName, int count, int timeout) {
        return null;
    }

//    @Override
//    public List<String> pop(String queueName, int count, int timeout, long leaseDurationSeconds) {
//        return null;
//    }

    @Override
    public void remove(String queueName, String messageId) {

    }

    @Override
    public int getSize(String queueName) {
        return 0;
    }

    @Override
    public boolean ack(String queueName, String messageId) {
        return false;
    }
}
