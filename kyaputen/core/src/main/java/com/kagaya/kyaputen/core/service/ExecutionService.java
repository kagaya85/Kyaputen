package com.kagaya.kyaputen.core.service;

import com.kagaya.kyaputen.common.metadata.tasks.TaskResult;
import com.kagaya.kyaputen.common.runtime.Workflow;
import com.kagaya.kyaputen.core.dao.QueueDAO;
import com.kagaya.kyaputen.common.metadata.tasks.Task;
import com.kagaya.kyaputen.common.metadata.tasks.Task.Status;
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

    private final QueueDAO queueDAO;
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

    public Task getPollTask(String taskType, String workId) {
        return getPollTask(taskType, workId, null);
    }

    public Task getPollTask(String taskType, String workerId, String domain) {
        List<Task> tasks = getPollTaskList(taskType, workerId, domain, 1, 100);
        
        if (tasks.isEmpty()) {
            return null;
        }
        else
            return tasks.get(0);
    }

    public List<Task> getPollTaskList(String taskType, String workerId, int count, int timeoutInMilliSecond) {
        return getPollTaskList(taskType, workerId, null, count, timeoutInMilliSecond);
    }

    public List<Task> getPollTaskList(String taskType, String workerId, String domain, int count, int timeoutMilliSecond) {
        if (timeoutMilliSecond > MAX_POLL_TIMEOUT_MS) {
            throw new ExecutionException(ExecutionException.Code.INVALID_INPUT,
                    "Long Poll Timeout value cannot be more than 5 seconds");
        }

        String queueName = QueueUtils.getQueueName(taskType, domain,null);

        List<String> taskIds = new LinkedList<>();
        List<Task> tasks = new LinkedList<>();

        /////////
        ///////
        //////

        try {
            taskIds = queueDAO.pop(queueName, count, timeoutMilliSecond);
        } catch (Exception e) {
            logger.error("Error polling for task: {} from worker: {} in domain: {}, count: {}", taskType, workerId,
                    domain, count, e);
        }

        for (String taskId : taskIds) {
            Task task = getTask(taskId);
            if (task == null || task.getStatus().isTerminal()) {
                // Remove taskId(s) without a valid Task/terminal state task from the queue
                queueDAO.remove(queueName, taskId);
                logger.debug("Removed taskId from the queue: {}, {}", queueName, taskId);
                continue;
            }

            task.setStatus(Status.IN_PROGRESS);
            if (task.getStartTime() == 0) {
                task.setStartTime(System.currentTimeMillis());
            }
            task.setWorkerId(workerId);
            task.setPollCount(task.getPollCount() + 1);
//            updateTask(task);
            tasks.add(task);
        }

        return tasks;
    }

    public void updateTask(Task task) {
        updateTask(new TaskResult(task));
    }

    public void updateTask(TaskResult taskResult) {
        workflowExecutor.updateTask(taskResult);
    }
//
    public Task getTask(String taskId) {
        return workflowExecutor.getTask(taskId);
    }
}
