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

    public boolean removeTask(String taskId) {

        return false;
    }


    public String createWorkflow(Workflow workflow) {

    }

    public String updateWorkflow(Workflow workflow) {

    }

    public Workflow getWorkflow(String workflowId) {

    }

    public long getInProgressTaskCount() {

    }

    public void updateLastPollData(String taskDefName, String domain, String workerId) {

    }

    public PollData getPollData(String taskDefName, String domain) {

    }

    public List<PollData> getPollData(String taskDefName) {

    }
}
