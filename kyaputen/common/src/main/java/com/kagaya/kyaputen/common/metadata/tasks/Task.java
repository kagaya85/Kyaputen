package com.kagaya.kyaputen.common.metadata.tasks;

import java.util.Map;
import java.util.Objects;

public class Task {

    // 1
    private String taskId;
    // 2
    private String taskType;
    // 3
    private Status status;
    // 4
    private String taskDefName;
    // 5
    private int retryCount;
    // 6
    private int seq;
    // 7
    private int pollCount;
    // 8
    private long scheduledTime;
    // 9
    private long startTime;
    // 10
    private long endTime;
    // 11
    private long updateTime;
    // 12
    private int startDelayInSeconds;
    // 13
    private boolean retried;
    // 14
    private boolean executed;
    // 15
    private String workerId;
    // 16
    private String domain;
    // 17
    private String executionNameSpace;
    // 18
    private Map<String, Object> inputData;
    // 19
    private Map<String, Object> outputData;
    // 20
    private String workflowInstanceId;
    // 21
    private int priority;
    // 22
    private String subWorkflowId;

    // not include in proto
    private TaskDefinition taskDefinition;

    private String nodeId;

    public Task() {

    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Map<String, Object> getInputData() {
        return inputData;
    }

    public void setInputData(Map<String, Object> inputData) {
        this.inputData = inputData;
    }

    public String getTaskDefName() {
        return taskDefName;
    }

    public void setTaskDefName(String taskDefName) {
        this.taskDefName = taskDefName;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public int getPollCount() {
        return pollCount;
    }

    public void setPollCount(int pollCount) {
        this.pollCount = pollCount;
    }

    public long getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(long scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public int getStartDelayInSeconds() {
        return startDelayInSeconds;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public void setStartDelayInSeconds(int startDelayInSeconds) {
        this.startDelayInSeconds = startDelayInSeconds;
    }

    public boolean isRetried() {
        return retried;
    }

    public void setRetried(boolean retried) {
        this.retried = retried;
    }

    @Deprecated
    public boolean isExecuted() {
        return executed;
    }

    @Deprecated
    public void setExecuted(boolean executed) {
        this.executed = executed;
    }

    public String getWorkerId() {
        return workerId;
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Map<String, Object> getOutputData() {
        return outputData;
    }

    public void setOutputData(Map<String, Object> outputData) {
        this.outputData = outputData;
    }

    public String getExecutionNameSpace() {
        return executionNameSpace;
    }

    public void setExecutionNameSpace(String executionNameSpace) {
        this.executionNameSpace = executionNameSpace;
    }

    public String getSubWorkflowId() {
        return subWorkflowId;
    }

    public void setSubWorkflowId(String subWorkflowId) {
        this.subWorkflowId = subWorkflowId;
    }

    public String getWorkflowInstanceId() {
        return workflowInstanceId;
    }

    public void setWorkflowInstanceId(String workflowInstanceId) {
        this.workflowInstanceId = workflowInstanceId;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public TaskDefinition getTaskDefinition() {
        return taskDefinition;
    }

    public void setTaskDefinition(TaskDefinition taskDefinition) {
        this.taskDefinition = taskDefinition;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public enum Status {
        IN_PROGRESS(false, true, true),
        CANCELED(true, false, false),
        FAILED(true, false, true),
        FAILED_WITH_TERMINAL_ERROR(true, false, false), //No Retires even if retries are configured, the task and the related workflow should be terminated
        COMPLETED(true, true, true),
        COMPLETED_WITH_ERRORS(true, true, true),
        SCHEDULED(false, true, true),
        TIMED_OUT(true, false, true),
        IN_QUEUE(false, true, false);

        private boolean terminal;

        private boolean successful;

        private boolean retriable;

        Status(boolean terminal, boolean successful, boolean retriable) {
            this.terminal = terminal;
            this.successful = successful;
            this.retriable = retriable;
        }

        public boolean isTerminal() {
            return terminal;
        }

        public boolean isSuccessful() {
            return successful;
        }

        public boolean isRetriable() {
            return retriable;
        }

        @Override
        public String toString() {
            return "Status{" +
                    "terminal=" + terminal +
                    ", successful=" + successful +
                    ", retriable=" + retriable +
                    '}';
        }
    }

    public Task copy() {
        Task task = new Task();

        task.setTaskId(taskId);
        task.setTaskType(taskType);
        task.setStatus(status);
        task.setTaskDefName(taskDefName);
        task.setRetryCount(retryCount);
        task.setPollCount(pollCount);
        task.setInputData(inputData);
        task.setOutputData(outputData);
        task.setExecuted(executed);
        task.setRetried(retried);
        task.setExecutionNameSpace(executionNameSpace);
        task.setStartTime(startTime);
        task.setScheduledTime(scheduledTime);
        task.setUpdateTime(updateTime);
        task.setWorkflowInstanceId(workflowInstanceId);
        task.setStartDelayInSeconds(startDelayInSeconds);
        task.setWorkerId(workerId);
        task.setPriority(priority);
        task.setSubWorkflowId(subWorkflowId);
        task.setEndTime(endTime);
        task.setDomain(domain);

        return task;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(getTaskId(), task.getTaskId()) &&
                Objects.equals(getTaskType(), task.getTaskType()) &&
                Objects.equals(getTaskDefName(), task.getTaskDefName()) &&
                Objects.equals(getWorkflowInstanceId(), task.getWorkflowInstanceId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTaskId(), getTaskType(), getTaskDefName(), getWorkflowInstanceId());
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskId='" + taskId + '\'' +
                ", taskType='" + taskType + '\'' +
                ", status=" + status +
                ", taskDefName='" + taskDefName + '\'' +
                ", workerId='" + workerId + '\'' +
                '}';
    }
}
