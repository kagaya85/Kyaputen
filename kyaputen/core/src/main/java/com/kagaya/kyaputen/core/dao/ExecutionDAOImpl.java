package com.kagaya.kyaputen.core.dao;

import com.kagaya.kyaputen.common.metadata.tasks.PollData;
import com.kagaya.kyaputen.common.metadata.tasks.Task;
import com.kagaya.kyaputen.common.metadata.tasks.TaskDefinition;
import com.kagaya.kyaputen.common.metadata.tasks.TaskResult;
import com.kagaya.kyaputen.common.metadata.workflow.WorkflowDefinition;
import com.kagaya.kyaputen.common.runtime.Workflow;
import com.kagaya.kyaputen.common.runtime.Workflow.WorkflowStatus;
import com.kagaya.kyaputen.core.events.Message;
import com.kagaya.kyaputen.core.utils.IdGenerator;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;

/**
 * @description 队列操作工具类
 */
public class ExecutionDAOImpl implements ExecutionDAO {

    private QueueDAO<Message> pollingQueue;

    private WorkflowRunQueue workflowQueue;

    @Inject
    ExecutionDAOImpl(QueueDAO<Message> pollingQueue) {
        this.pollingQueue = pollingQueue;
    }

    @Override
    public void updateTask(Task task, TaskResult taskResult) {
        Workflow workflow = workflowQueue.get(task.getWorkflowInstanceId());
        Task t = workflow.getTask(task.getTaskId());

        t.setOutputData(taskResult.getOutputData());
    }

    @Override
    public Task getTask(String workflowId, String taskId) {
        Workflow workflow = workflowQueue.get(workflowId);
        Task t = workflow.getTask(taskId);

        return t;
    }

    @Override
    public boolean removeTask(String taskId) {

        return false;
    }


    @Override
    public boolean createWorkflow(WorkflowDefinition workflowDef) {

        Workflow workflow = new Workflow();
        String workflowId = IdGenerator.generate();

        workflow.setStatus(WorkflowStatus.RUNNING);
        workflow.setCreateTime(System.currentTimeMillis());
        workflow.setWorkflowDefinition(workflowDef);
        workflow.setWorkflowId(workflowId);
        workflow.setTasks(createTaskQueue(workflowId, workflowDef.getTaskDefs()));

        workflowQueue.add(workflow);

        return false;
    }

    public List<Task> createTaskQueue(String workflowId, List<TaskDefinition> taskDefs) {

        List<Task> taskQueue = new LinkedList<>();

        for (TaskDefinition taskDef: taskDefs) {
            Task task = new Task();

            task.setWorkflowInstanceId(workflowId);
            task.setTaskDefName(taskDef.getTaskDefName());
            task.setExecuted(false);
            task.setPollCount(0);
            task.setTaskId(IdGenerator.generate());
            task.setRetryCount(0);

            taskQueue.add(task);
        }

        return taskQueue;
    }

    @Override
    public void updateWorkflow(Workflow workflow) {
        Workflow w = workflowQueue.get(workflow.getWorkflowId());

    }

    @Override
    public Workflow getWorkflow(String workflowId) {
        return workflowQueue.get(workflowId);
    }

    @Override
    public long getInProgressTaskCount() {

        return -1;
    }

    @Override
    public PollData getPollData(String taskDefName, String domain) {

        return new PollData();
    }

}
