package com.kagaya.kyaputen.core.service;

import com.kagaya.kyaputen.common.metadata.tasks.Task;
import com.kagaya.kyaputen.common.runtime.Workflow;

import java.util.LinkedList;
import java.util.List;

public class DecideService {

    /**
     *
     * @param workflowd
     * @description 筛选待执行任务与待更新任务，传递参数
     * @return DecideOutcome
     */
    public DecideOutcome decide(final Workflow workflowd) {





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


