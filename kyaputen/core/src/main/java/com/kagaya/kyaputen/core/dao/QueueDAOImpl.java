package com.kagaya.kyaputen.core.dao;

import com.kagaya.kyaputen.core.events.Message;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class QueueDAOImpl implements QueueDAO {
    private Queue<Message> queue = new LinkedList<Message>();

    @Override
    public void push(String queueName, String id) {
    }

    @Override
    public void push(String queueName, List<Message> messages) {

    }

    @Override
    public void push(String queueName, String id, int priority, long offsetTimeInSecond) {

    }

    @Override
    public List<String> pop(String queueName, int count, int timeout) {
        return null;
    }

    @Override
    public List<String> pop(String queueName, int count, int timeout, long leaseDurationSeconds) {
        return null;
    }

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
