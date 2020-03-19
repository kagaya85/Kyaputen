package com.kagaya.kyaputen.common.metadata.tasks;

import java.util.Objects;

public class Poll {
    private String queueName;

    private String taskType;

    private String workerId;

    private String domain;

    private String lastPollTime;

    public Poll() {
        super();
    }

    public Poll (String queueName, String workerId, String taskType, String domain, String lastPollTime) {
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
        Poll poll = (Poll) o;
        return Objects.equals(getQueueName(), poll.getQueueName()) &&
                Objects.equals(getTaskType(), poll.getTaskType()) &&
                Objects.equals(getWorkerId(), poll.getWorkerId()) &&
                Objects.equals(getDomain(), poll.getDomain()) &&
                Objects.equals(getLastPollTime(), poll.getLastPollTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getQueueName(), getTaskType(), getWorkerId(), getDomain(), getLastPollTime());
    }
}
