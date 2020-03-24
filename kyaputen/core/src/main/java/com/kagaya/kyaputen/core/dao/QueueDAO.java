package com.kagaya.kyaputen.core.dao;

public interface QueueDAO {

    void push(String queueName, String id);
}
