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

    private QueueDAO<Workflow> workflowQueue;

    @Inject
    ExecutionDAOImpl(QueueDAO<Task> taskQueue, QueueDAO<TaskDefinition> taskDefQueue, QueueDAO<Message> pollingQueue) {

        this.taskQueue = taskQueue;
        this.taskDefQueue = taskDefQueue;
        this.pollingQueue = pollingQueue;

    }

    @Override
    public void updateTask(Task task, TaskResult taskResult) {
        Task t = taskQueue.get(task.getTaskDefName(), task.getTaskId());

        t.setOutputData(taskResult.getOutputData());
    }

    @Override
    public Task getTask(String queueName, String taskId) {
        return taskQueue.get(queueName, taskId);
    }

    @Override
    public boolean removeTask(String taskId) {

        return false;
    }


    @Override
    public String createWorkflow(Workflow workflow) {

    }

    @Override
    public String updateWorkflow(Workflow workflow) {

    }

    @Override
    public Workflow getWorkflow(String workflowId) {

    }

    @Override
    public long getInProgressTaskCount() {

    }

    @Override
    public void updateLastPollData(String taskDefName, String domain, String workerId) {

    }

    @Override
    public PollData getPollData(String taskDefName, String domain) {

    }

    @Override
    public List<PollData> getPollData(String taskDefName) {

    }
}
