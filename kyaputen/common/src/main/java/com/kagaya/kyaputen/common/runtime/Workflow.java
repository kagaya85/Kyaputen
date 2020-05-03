package com.kagaya.kyaputen.common.runtime;

import com.kagaya.kyaputen.common.metadata.tasks.Task;
import com.kagaya.kyaputen.common.metadata.tasks.TaskDefinition;
import com.kagaya.kyaputen.common.metadata.workflow.WorkflowDefinition;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @description runtime工作流状态管理
 */
public class Workflow {

    public enum  WorkflowStatus {
        RUNNING(false, false), COMPLETED(true, true),
        FAILED(true, false), TIMED_OUT(true, false),
        TERMINATED(true, false), PAUSED(false, true),
        READY(false, false);

        private boolean terminal;

        private boolean successful;

        WorkflowStatus(boolean terminal, boolean successful){
            this.terminal = terminal;
            this.successful = successful;
        }

        public boolean isTerminal(){
            return terminal;
        }

        public boolean isSuccessful(){
            return successful;
        }
    }

    private String name;

    private WorkflowStatus status = WorkflowStatus.RUNNING;

    private Long createTime;

    private Long startTime;

    private Long updateTime;

    private Long endTime;

    private String workflowId;

    private Map<String, Task> tasks = new HashMap<>();

    private Map<String, Object> input = new HashMap<>();

    private Map<String, Object> output = new HashMap<>();;

    private WorkflowDefinition workflowDefinition;

    private int priority;

    public Task getTaskById(String taskId) {
        for (Task task: tasks.values()) {
            if (task.getTaskId().equals(taskId))
                return task;
        }

        return null;
    }

    public Task getTask(String taskName) {
        return tasks.get(taskName);
    }

    public void setTasks(Map<String, Task> tasks) {
        this.tasks = tasks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public WorkflowStatus getStatus() {
        return status;
    }

    public void setStatus(WorkflowStatus status) {
        this.status = status;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Map<String, Object> getInput() {
        return input;
    }

    public void setInput(Map<String, Object> input) {
        this.input = input;
    }

    public Map<String, Object> getOutput() {
        return output;
    }

    public void setOutput(Map<String, Object> output) {
        this.output = output;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    public WorkflowDefinition getWorkflowDefinition() {
        return workflowDefinition;
    }

    public void setWorkflowDefinition(WorkflowDefinition workflowDefinition) {
        this.workflowDefinition = workflowDefinition;
    }
}
