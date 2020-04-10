package com.kagaya.kyaputen.core.dao;

import com.kagaya.kyaputen.common.metadata.tasks.Task;

import java.util.List;

public interface QueueDAO<T> {

    void push(String queueName, T Item);

    /**
     *
     * @param queueName
     * @return 队列中第一个任务，出队列
     */
    T get(String queueName);

    /**
     *
     * @param queueName
     * @return 查看队列中第一个任务
     */
    T peek(String queueName);

    void remove(String queueName, String id);

    int getSize(String queueName);

    boolean isEmpty(String queueName);

}
