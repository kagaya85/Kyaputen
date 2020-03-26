package com.kagaya.kyaputen.common.metadata.tasks;

import java.util.Map;
import com.google.protobuf.Any;
import com.kagaya.kyaputen.common.runtime.Resource;

public class Task {

    private String taskId;

    private String taskType;

    private Status status;

    private String referenceTaskName;

    private int retryCount;

    private int seq;

    private int pollCount;

    private long scheduledTime;

    private long startTime;

    private long endTime;

    private long updateTime;

    private int startDelayInSeconds;

    private boolean retried;

    private boolean executed;

    private String workerId;

    private String domain;

    private String executionNameSpace;

    private Map<String, Object> inputData;

    private Map<String, Object> outputData;

    private String workflowInstanceId;

    private String workflowPriority;

    private String subWorkflowId;

    public final Resource reource = new Resource();

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

    public String getReferenceTaskName() {
        return referenceTaskName;
    }

    public void setReferenceTaskName(String referenceTaskName) {
        this.referenceTaskName = referenceTaskName;
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

    public boolean isExecuted() {
        return executed;
    }

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

    public String getWorkflowPriority() {
        return workflowPriority;
    }

    public void setWorkflowPriority(String workflowPriority) {
        this.workflowPriority = workflowPriority;
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
        SKIPPED(true, true, false);

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
}
