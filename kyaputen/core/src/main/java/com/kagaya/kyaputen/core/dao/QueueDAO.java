package com.kagaya.kyaputen.core.dao;

import com.kagaya.kyaputen.common.metadata.tasks.Task;
import com.kagaya.kyaputen.core.events.TaskMessage;

import java.util.List;

public interface QueueDAO {

    void push(String queueName, TaskMessage Item);

    void pushIfNotExists(String queueName, TaskMessage item);

    /**
     *
     * @param queueName
     * @return 弹出队列中的任务
     */
    TaskMessage pop(String queueName);

    TaskMessage pop(String queueName, String workerId);

    /**
     *
     * @param queueName
     * @return 获取队列中第一个任务，保持在队列中
     */
    TaskMessage get(String queueName);

    /**
     *
     * @param queueName
     * @param id
     * @return target object if exist, or null
     */
    TaskMessage get(String queueName, String id);

    void remove(String queueName, String id);

    int getSize(String queueName);

    boolean isEmpty(String queueName);

}
