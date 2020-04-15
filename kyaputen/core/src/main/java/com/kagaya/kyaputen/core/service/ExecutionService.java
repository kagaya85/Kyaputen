package com.kagaya.kyaputen.core.service;

import com.kagaya.kyaputen.common.metadata.tasks.TaskResult;
import com.kagaya.kyaputen.common.runtime.Workflow;
import com.kagaya.kyaputen.core.dao.QueueDAO;
import com.kagaya.kyaputen.common.metadata.tasks.Task;
import com.kagaya.kyaputen.common.metadata.tasks.Task.Status;
import com.kagaya.kyaputen.core.events.TaskMessage;
import com.kagaya.kyaputen.core.execution.ExecutionException;
import com.kagaya.kyaputen.core.execution.WorkflowExecutor;
import com.kagaya.kyaputen.core.utils.QueueUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @description 面向Worker的任务轮询处理逻辑
 */

@Singleton
public class ExecutionService {

    private static final Logger logger = LoggerFactory.getLogger(ExecutionService.class);

    private final QueueDAO<TaskMessage> queueDAO;
    private final WorkflowExecutor workflowExecutor;

    private static final int MAX_POLL_TIMEOUT_MS = 5000;
    private static final int POLL_COUNT_ONE = 1;
    private static final int POLLING_TIMEOUT_IN_MS = 100;

    private static final int MAX_SEARCH_SIZE = 5_000;

    @Inject
    public ExecutionService(QueueDAO queueDAO,
                            WorkflowExecutor workflowExecutor) {
        this.queueDAO = queueDAO;
        this.workflowExecutor = workflowExecutor;
    }

    public Task getPollTask(String taskName, String workerId, int count, int timeoutInMilliSecond) {
        return getPollTask(taskName, workerId, null, count, timeoutInMilliSecond);
    }

    /**
     * 获取就绪任务列表
     * @param taskName 任务名称
     * @param workerId workerId
     * @param domain 所属domain
     * @param count
     * @param timeoutMilliSecond
     * @return
     */
    public Task getPollTask(String taskName, String workerId, String domain, int count, int timeoutMilliSecond) {
        if (timeoutMilliSecond > MAX_POLL_TIMEOUT_MS) {
            throw new ExecutionException(ExecutionException.Code.INVALID_INPUT,
                    "Long Poll Timeout value cannot be more than 5 seconds");
        }

        String queueName = QueueUtils.getQueueName(taskName, domain,null);

        Task task;
        String taskId;

        while (true) {
            TaskMessage taskMessage = queueDAO.get(queueName, workerId);
            if (taskMessage == null) {
                return null;
            }
            taskId = taskMessage.getTaskId();
            task = getTask(taskMessage.getWorkflowInstanceId(), taskId);
            if (task.getStatus().isTerminal()) {
                logger.debug("task {} was already terminated in queue: {}", taskId, queueName);
            } else
                break;
        }

        task.setStatus(Status.IN_PROGRESS);
        task.setStartTime(System.currentTimeMillis());
        task.setPollCount(task.getPollCount() + 1);

        return task;
    }

    public void updateTask(Task task) {
        updateTask(new TaskResult(task));
    }

    public void updateTask(TaskResult taskResult) {
        workflowExecutor.updateTask(taskResult);
    }

    public Task getTask(String workflowId, String taskId) {
        return workflowExecutor.getTask(workflowId, taskId);
    }

    public void removeTaskFromQueue(String worflowId, String taskId) {

    }

    public void removeWorkflow(String workflowId, boolean finishWorkflow) {

    }

    public void terminateWorkflow(String workflowId) {

    }

    public Workflow getWorkflowInstance(String workflowName) {

        return new Workflow();
    }

    public List<String> getRunningWorkflows(String workflowName) {

        return new LinkedList<String>();
    }

}
