package com.kagaya.kyaputen.core.dao;

import com.kagaya.kyaputen.common.metadata.tasks.PollData;
import com.kagaya.kyaputen.common.metadata.tasks.Task;
import com.kagaya.kyaputen.common.metadata.tasks.TaskDefinition;
import com.kagaya.kyaputen.common.metadata.tasks.TaskResult;
import com.kagaya.kyaputen.common.runtime.Workflow;
import com.kagaya.kyaputen.core.events.Message;

import javax.inject.Inject;
import java.util.List;

/**
 * @description 队列操作工具类
 */
public class ExecutionDAOImpl implements ExecutionDAO {

    private QueueDAO<Task> taskQueue;

    private QueueDAO<TaskDefinition> taskDefQueue;

    private QueueDAO<Message> pollingQueue;
    

    @Inject
    ExecutionDAOImpl(QueueDAO<Task> taskQueue, QueueDAO<TaskDefinition> taskDefQueue, QueueDAO<Message> pollingQueue) {

        this.taskQueue = taskQueue;
        this.taskDefQueue = taskDefQueue;
        this.pollingQueue = pollingQueue;

    }

    @Override
    void updateTask(Task task, TaskResult taskResult) {
    }

    boolean removeTask(String taskId) {

        return false;
    }

    Task getTask(String taskId) {

    }

    String createWorkflow(Workflow workflow) {

    }

    String updateWorkflow(Workflow workflow) {

    }

    Workflow getWorkflow(String workflowId) {

    }

    long getInProgressTaskCount() {

    }

    void updateLastPollData(String taskDefName, String domain, String workerId) {

    }

    PollData getPollData(String taskDefName, String domain) {

    }

    List<PollData> getPollData(String taskDefName) {

    }
}
