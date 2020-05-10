package com.kagaya.kyaputen.core.service;

import com.kagaya.kyaputen.common.metadata.tasks.Task;
import com.kagaya.kyaputen.common.metadata.tasks.Task.Status;
import com.kagaya.kyaputen.common.metadata.tasks.TaskDefinition;
import com.kagaya.kyaputen.common.runtime.Workflow;
import com.kagaya.kyaputen.core.dao.ExecutionDAO;
import com.kagaya.kyaputen.core.dao.ExecutionDAOImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;

public class DecideService {

    @Inject
    ExecutionDAO executionDAO;

    private static final Logger logger = LoggerFactory.getLogger(DecideService.class);

//    @Inject
//    public DecideService(ExecutionDAO executionDAO) {
//        this.executionDAO = executionDAO;
//    }

    /**
     * 返回需要执行的任务列表和需要更新的任务列表
     * @param workflow 工作流实例对象
     * @return DecideOutcome
     */
    public DecideOutcome decide(Workflow workflow) {
        DecideOutcome outcome = new DecideOutcome();

        List<String> taskNames = workflow.getTaskNames();

        for (String n: taskNames) {
            Task task = workflow.getTask(n);
            if (task.getStatus().equals(Status.IN_QUEUE) && checkReady(task)) {
                outcome.tasksToBeScheduled.add(task);
            }
        }
        logger.debug("Decide workflow: {}, taskToBeScheduled: {}", workflow.getWorkflowId(), outcome.tasksToBeScheduled);
        return outcome;
    }

    /**
     * 检查任务是否就绪
     * @param task
     * @return
     */
    private boolean checkReady(Task task) {
        TaskDefinition taskDef = task.getTaskDefinition();

        List<String> priorTasks = taskDef.getPriorTasks();
        if (priorTasks.size() == 0)
            return true;

        Workflow workflow = executionDAO.getWorkflow(task.getWorkflowInstanceId());

        for (String taskName: priorTasks) {
            Task t = workflow.getTask(taskName);
            if (null == t || !t.getStatus().equals(Status.COMPLETED)) {
                logger.debug("Check Task: {} - {} is not ready", task.getTaskDefName(), task.getTaskId());
                return false;
            }
        }

        logger.debug("Check Task: {} - {} is ready", task.getTaskDefName(), task.getTaskId());
        return true;
    }

    public static class DecideOutcome {

        public List<Task> tasksToBeScheduled = new LinkedList<>();

        public List<Task> tasksToBeUpdated = new LinkedList<>();

        public boolean isComplete;

        public DecideOutcome() {
        }

    }
}


