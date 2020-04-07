package com.kagaya.kyaputen.core.dao;

import com.kagaya.kyaputen.common.metadata.tasks.Task;

import java.util.List;

public interface QueueDAO {

    void push(String queueName, Task task);

    /**
     *
     * @param queueName
     * @return 队列中第一个任务，出队列
     */
    Task get(String queueName);

    /**
     *
     * @param queueName
     * @return 查看队列中第一个任务
     */
    Task peek(String queueName);

    void remove(String queueName, String id);

    int getSize(String queueName);

    boolean isEmpty(String queueName);

}
