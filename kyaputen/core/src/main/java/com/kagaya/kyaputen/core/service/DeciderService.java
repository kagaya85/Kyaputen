package com.kagaya.kyaputen.core.service;

import com.kagaya.kyaputen.common.metadata.tasks.Task;
import com.kagaya.kyaputen.common.runtime.Workflow;

import java.util.LinkedList;
import java.util.List;

public class DeciderService {

    public DeciderOutcome decide(final Workflow workflowd) {





        return new DeciderOutcome();
    }

    public static class DeciderOutcome {

        public List<Task> tasksToBeScheduled = new LinkedList<>();

        public List<Task> tasksToBeUpdated = new LinkedList<>();

        public boolean isComplete;

        public DeciderOutcome() {
        }

    }
}


