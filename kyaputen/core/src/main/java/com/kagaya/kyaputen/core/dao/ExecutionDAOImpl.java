package com.kagaya.kyaputen.core.dao;

import com.kagaya.kyaputen.common.metadata.tasks.PollData;
import com.kagaya.kyaputen.common.metadata.tasks.Task;
import com.kagaya.kyaputen.common.metadata.tasks.TaskDefinition;
import com.kagaya.kyaputen.common.metadata.tasks.TaskResult;
import com.kagaya.kyaputen.common.metadata.workflow.WorkflowDefinition;
import com.kagaya.kyaputen.common.runtime.Workflow;
import com.kagaya.kyaputen.common.runtime.Workflow.WorkflowStatus;
import com.kagaya.kyaputen.common.schedule.ExecutionPlan;
import com.kagaya.kyaputen.core.events.TaskMessage;
import com.kagaya.kyaputen.core.utils.IdGenerator;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
    public Workflow createWorkflow(WorkflowDefinition workflowDef, ExecutionPlan plan) {

        Workflow workflow = new Workflow();
        String workflowId = IdGenerator.generate();

        workflow.setName(workflowDef.getName());
        workflow.setStatus(WorkflowStatus.READY);
        workflow.setCreateTime(System.currentTimeMillis());
        workflow.setWorkflowDefinition(workflowDef);
        workflow.setWorkflowId(workflowId);
        workflow.setTasks(createTaskMap(workflowId, workflowDef, plan));

        workflowQueue.add(workflow);

        return workflow;
    }

    public Map<String, Task> createTaskMap(String workflowId, WorkflowDefinition workflowDef, ExecutionPlan plan) {

        Map<String, Task> taskMap = new HashMap<>();
        List<String> taskDefNames = workflowDef.getTaskDefNames();

        for (String tdn: taskDefNames) {
            TaskDefinition taskDef = workflowDef.getTaskDef(tdn);
            Task task = new Task();
            String taskName = taskDef.getTaskDefName();

            task.setWorkflowInstanceId(workflowId);
            task.setTaskDefName(taskName);
            task.setExecuted(false);
            task.setPollCount(0);
            task.setTaskId(plan.getTaskExecutionPlan(taskName).getTaskId());
            task.setRetryCount(0);
            task.setPriority(taskDef.getPriority());
            task.setTaskDefinition(taskDef);
            task.setStartTime(0);

            taskMap.put(taskName, task);
        }

        return taskMap;
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
