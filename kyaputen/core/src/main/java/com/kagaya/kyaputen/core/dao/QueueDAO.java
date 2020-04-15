package com.kagaya.kyaputen.core.dao;

import com.kagaya.kyaputen.common.metadata.tasks.Task;

import java.util.List;

public interface QueueDAO<T> {

    void push(String queueName, T Item);

    void pushIfNotExists(String queueName, T item);

    /**
     *
     * @param queueName
     * @return 弹出队列中的任务
     */
    T pop(String queueName);

    T pop(String queueName, String workerId);

    /**
     *
     * @param queueName
     * @return 获取队列中第一个任务，保持在队列中
     */
    T get(String queueName);

    /**
     *
     * @param queueName
     * @param id
     * @return target object if exist, or null
     */
    T get(String queueName, String id);

    void remove(String queueName, String id);

    int getSize(String queueName);

    boolean isEmpty(String queueName);

}
