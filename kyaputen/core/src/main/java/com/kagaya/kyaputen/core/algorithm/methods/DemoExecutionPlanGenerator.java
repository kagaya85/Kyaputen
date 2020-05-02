package com.kagaya.kyaputen.core.algorithm.methods;

import com.kagaya.kyaputen.common.metadata.tasks.TaskDefinition;
import com.kagaya.kyaputen.common.metadata.workflow.WorkflowDefinition;
import com.kagaya.kyaputen.common.runtime.Pod;
import com.kagaya.kyaputen.common.schedule.ExecutionPlan;
import com.kagaya.kyaputen.common.schedule.TaskExecutionPlan;
import com.kagaya.kyaputen.core.dao.PodResourceDAO;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class DemoExecutionPlanGenerator implements Method {

    private ExecutionPlan exePlan = null;

    private WorkflowDefinition workflowDef = null;

    private PodResourceDAO podResource = new PodResourceDAO();

    public DemoExecutionPlanGenerator(WorkflowDefinition workflowDef) {
        this.workflowDef = workflowDef;
    }

    @Override
    public ExecutionPlan schedule(long startTime) {

        exePlan = new ExecutionPlan(workflowDef);

        calcUrgencyLevel();

        genExecutionPlan(startTime);

        return exePlan;
    }

    private void genExecutionPlan(long startTime) {
        List<String> taskQueue = new LinkedList<>(workflowDef.getTaskDefNames());

        taskQueue.sort(new TaskUrgencyLevelComparator());

        while (!taskQueue.isEmpty()) {
            String taskName = taskQueue.get(0);
            taskQueue.remove(0);
            TaskExecutionPlan taskExecutionPlan = exePlan.getTaskExecutionPlan(taskName);


            String podId = allocatePod(taskExecutionPlan);

            Pod pod = podResource.getPod(podId);

            if (pod.getStatus().equals(Pod.PodStatus.NEW)) {
                // 若为新Pod，则分配Node


            }

        }
    }

    /**
     * 为任务分配Pod
     * @param plan
     * @return
     */
    private String allocatePod(TaskExecutionPlan plan) {
        String podId = null;

        for (String pid: podResource.getPodIdList()) {
            Pod pod = podResource.getPod(pid);

            // 镜像类型不符合或者已经结束的Pod
            if (!pod.getTaskImageName().equals(plan.getTaskType()) || pod.getStatus().equals(Pod.PodStatus.DOWN) || pod.getStatus().equals(Pod.PodStatus.ERROR))
                continue;




        }

        // 更新pod分配map

        plan.setPodId(podId);
        return podId;
    }

    private void calcUrgencyLevel() {

        for (String tdn: workflowDef.getTaskDefNames()) {
            TaskDefinition td = workflowDef.getTaskDef(tdn);
            TaskExecutionPlan plan = exePlan.getTaskExecutionPlan(tdn);
            plan.setUrgencyLevel(td.getExpectedStartTime() + td.getTimeLimit() - td.getExpectedFinishTime());
        }
    }

    private class TaskUrgencyLevelComparator implements Comparator<String> {

        @Override
        public int compare(String n1, String n2) {
            // 升序，紧急程度降序
            double u1 = exePlan.getTaskExecutionPlan(n1).getUrgencyLevel();
            double u2 = exePlan.getTaskExecutionPlan(n2).getUrgencyLevel();
            if (u1 > u2)
                return -1;
            else
                return 0;
        }
    }
}
