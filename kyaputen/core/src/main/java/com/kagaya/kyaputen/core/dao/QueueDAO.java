package com.kagaya.kyaputen.core.dao;

import com.kagaya.kyaputen.core.events.Message;

import java.util.List;
import java.util.Map;

public interface QueueDAO {

    void push(String queueName, String id);

    void push(String queueName, String id, Message.MessageType type);

    void push(String queueName, String id, int priority, Message.MessageType type);

    void push(String queueName, Message message);

    void push(String queueName, List<Message> messages);

    List<String> pop(String queueName, int count, int timeout);

//    default List<String> pop(String queueName, int count, int timeout, long leaseDurationSeconds) {
//        return pop(queueName, count, timeout);
//    }

    void remove(String queueName, String messageId);

    int getSize(String queueName);

    /**
     *
     * @param queueName Name of the queue
     * @param messageId Message Id
     * @return true if the message was found and ack'ed
     */
    boolean ack(String queueName, String messageId);
}
