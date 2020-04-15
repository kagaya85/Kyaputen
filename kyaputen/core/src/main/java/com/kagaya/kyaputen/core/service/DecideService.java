package com.kagaya.kyaputen.core.service;

import com.kagaya.kyaputen.common.metadata.tasks.Task;
import com.kagaya.kyaputen.common.metadata.tasks.Task.Status;
import com.kagaya.kyaputen.common.runtime.Workflow;
import com.kagaya.kyaputen.core.dao.ExecutionDAO;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;

public class DecideService {

    private ExecutionDAO executionDAO;

    @Inject
    public DecideService(ExecutionDAO executionDAO) {
        this.executionDAO = executionDAO;
    }
    /**
     *
     * @param workflowId
     * @description 
     * @return DecideOutcome
     */
    public DecideOutcome decide(String workflowId) {
        Workflow workflow = executionDAO.getWorkflow(workflowId);

        DecideOutcome outcome = new DecideOutcome();

        List<Task> taskList = workflow.getTasks();

        for (Task task: taskList) {
            if (task.getStatus().equals(Status.SCHEDULED)) {
                outcome.tasksToBeScheduled.add(task);
            }
        }

        return outcome;
    }

    public static class DecideOutcome {

        public List<Task> tasksToBeScheduled = new LinkedList<>();

        public List<Task> tasksToBeUpdated = new LinkedList<>();

        public boolean isComplete;

        public DecideOutcome() {
        }

    }
}


