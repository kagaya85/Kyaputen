package com.kagaya.kyaputen.core.service;

import com.kagaya.kyaputen.common.metadata.tasks.Task;
import com.kagaya.kyaputen.common.metadata.tasks.Task.Status;
import com.kagaya.kyaputen.common.runtime.Workflow;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;

public class DecideService {

    /**
     * 返回需要执行的任务列表和需要更新的任务列表
     * @param workflow 工作流实例对象
     * @return DecideOutcome
     */
    public DecideOutcome decide(Workflow workflow) {
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


