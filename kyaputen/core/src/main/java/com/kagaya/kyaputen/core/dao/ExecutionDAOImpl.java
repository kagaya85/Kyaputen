package com.kagaya.kyaputen.core.dao;

import com.kagaya.kyaputen.common.metadata.tasks.PollData;
import com.kagaya.kyaputen.common.metadata.tasks.Task;
import com.kagaya.kyaputen.common.metadata.tasks.TaskDefinition;
import com.kagaya.kyaputen.common.metadata.tasks.TaskResult;
import com.kagaya.kyaputen.common.metadata.workflow.WorkflowDefinition;
import com.kagaya.kyaputen.common.runtime.Workflow;
import com.kagaya.kyaputen.common.runtime.Workflow.WorkflowStatus;
import com.kagaya.kyaputen.core.events.TaskMessage;
import com.kagaya.kyaputen.core.utils.IdGenerator;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;

/**
 * 队列操作工具类
 */
public class ExecutionDAOImpl implements ExecutionDAO {

    private WorkflowQueue workflowQueue;

    @Inject
    ExecutionDAOImpl() {

    }

    @Override
    public void updateTask(Task task, TaskResult taskResult) {
        Workflow workflow = workflowQueue.getById(task.getWorkflowInstanceId());
        Task t = workflow.getTask(task.getTaskId());

        t.setOutputData(taskResult.getOutputData());
    }

    @Override
    public Task getTask(String workflowId, String taskId) {

        Workflow workflow = workflowQueue.getById(workflowId);

        return workflow.getTask(taskId);
    }

    @Override
    public boolean removeTask(String taskId) {

        return false;
    }


    @Override
    public Workflow createWorkflow(WorkflowDefinition workflowDef) {

        Workflow workflow = new Workflow();
        String workflowId = IdGenerator.generate();

        workflow.setName(workflowDef.getName());
        workflow.setStatus(WorkflowStatus.READY);
        workflow.setCreateTime(System.currentTimeMillis());
        workflow.setWorkflowDefinition(workflowDef);
        workflow.setWorkflowId(workflowId);
        workflow.setTasks(createTaskQueue(workflowId, workflowDef.getTaskDefs()));

        workflowQueue.add(workflow);

        return workflow;
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
            task.setPriority(taskDef.getPriority());
            task.setTaskDefinition(taskDef);
            task.setStartTime(0);

            taskQueue.add(task);
        }

        return taskQueue;
    }

    @Override
    public boolean updateWorkflow(Workflow workflow) {
        Workflow w = workflowQueue.getById(workflow.getWorkflowId());

        return false;
    }

    @Override
    public Workflow getWorkflow(String workflowId) {
        return workflowQueue.getById(workflowId);
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
