package com.kagaya.kyaputen.common.metadata.tasks;

import java.util.Objects;

public class PollData {
    private String queueName;

    private String taskType;

    private String workerId;

    private String domain;

    private String lastPollTime;

    public PollData() {
        super();
    }

    public PollData(String queueName, String workerId, String taskType, String domain, String lastPollTime) {
        super();
        this.queueName = queueName;
        this.workerId = workerId;
        this.taskType = taskType;
        this.domain = domain;
        this.lastPollTime = lastPollTime;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public String getWorkerId() {
        return workerId;
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getLastPollTime() {
        return lastPollTime;
    }

    public void setLastPollTime(String lastPollTime) {
        this.lastPollTime = lastPollTime;
    }

    @Override
    public String toString() {
        return "Poll{" +
                "queueName='" + queueName + '\'' +
                ", taskType='" + taskType + '\'' +
                ", workerId='" + workerId + '\'' +
                ", domain='" + domain + '\'' +
                ", lastPollTime='" + lastPollTime + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PollData pollData = (PollData) o;
        return Objects.equals(getQueueName(), pollData.getQueueName()) &&
                Objects.equals(getTaskType(), pollData.getTaskType()) &&
                Objects.equals(getWorkerId(), pollData.getWorkerId()) &&
                Objects.equals(getDomain(), pollData.getDomain()) &&
                Objects.equals(getLastPollTime(), pollData.getLastPollTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getQueueName(), getTaskType(), getWorkerId(), getDomain(), getLastPollTime());
    }
}
