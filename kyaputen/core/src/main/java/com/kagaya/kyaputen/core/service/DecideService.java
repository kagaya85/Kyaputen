package com.kagaya.kyaputen.core.service;

import com.kagaya.kyaputen.common.metadata.tasks.Task;
import com.kagaya.kyaputen.common.runtime.Workflow;

import java.util.LinkedList;
import java.util.List;

public class DecideService {

    /**
     *
     * @param workflowId
     * @description 
     * @return DecideOutcome
     */
    public DecideOutcome decide(final Workflow workflowId) {





        return new DecideOutcome();
    }

    public static class DecideOutcome {

        public List<Task> tasksToBeScheduled = new LinkedList<>();

        public List<Task> tasksToBeUpdated = new LinkedList<>();

        public boolean isComplete;

        public DecideOutcome() {
        }

    }
}


