package com.kagaya.kyaputen.core.algorithm.methods;

import com.kagaya.kyaputen.common.metadata.tasks.TaskDefinition;
import com.kagaya.kyaputen.common.metadata.workflow.WorkflowDefinition;
import com.kagaya.kyaputen.common.runtime.Pod;
import com.kagaya.kyaputen.common.schedule.ExecutionPlan;
import com.kagaya.kyaputen.common.schedule.TaskExecutionPlan;
import com.kagaya.kyaputen.core.dao.PodResourceDAO;
import com.kagaya.kyaputen.core.utils.IdGenerator;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class DemoExecutionPlanGenerator implements Method {

    private ExecutionPlan exePlan = null;

    private WorkflowDefinition workflowDef = null;

    private PodResourceDAO podResource = new PodResourceDAO();

    // 工作流开始的现实时间
    private long startTime;

    public DemoExecutionPlanGenerator(WorkflowDefinition workflowDef, long startTime) {
        this.workflowDef = workflowDef;
        this.startTime = startTime;
    }

    @Override
    public ExecutionPlan schedule() {

        exePlan = new ExecutionPlan(workflowDef);

        calcUrgencyLevel();

        genExecutionPlan();

        return exePlan;
    }

    private void genExecutionPlan() {
        List<String> taskQueue = new LinkedList<>(workflowDef.getTaskDefNames());

        taskQueue.sort(new TaskUrgencyLevelComparator());

        while (!taskQueue.isEmpty()) {
            String taskName = taskQueue.get(0);
            taskQueue.remove(0);

            TaskDefinition taskDef = workflowDef.getTaskDef(taskName);
            TaskExecutionPlan taskExecutionPlan = exePlan.getTaskExecutionPlan(taskName);

            // 设置task基础属性
            taskExecutionPlan.setExecutionTime(taskDef.getAbsoluteFinishTime() - taskDef.getAbsoluteStartTime());
            taskExecutionPlan.setTaskId(IdGenerator.generate());

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
        TaskDefinition taskDef = workflowDef.getTaskDef(plan.getTaskName());
        long executionTime, finishTime;
        for (String pid: podResource.getPodIdList()) {
            Pod pod = podResource.getPod(pid);

            // 镜像类型不符合或者已经结束的Pod
            if (!pod.getTaskImageName().equals(plan.getTaskType()) || pod.getStatus().equals(Pod.PodStatus.DOWN) || pod.getStatus().equals(Pod.PodStatus.ERROR))
                continue;

            executionTime = (long)Math.ceil(taskDef.getTaskSize() / pod.getComputeUnit());
            long deadline = startTime + taskDef.getAbsoluteStartTime() + taskDef.getTimeLimit();

            if (pod.getEarliestStartTime() + executionTime < deadline) {
                // 满足时间需求的情况下，选择最优pod

                podId = pid;
            }

        }

        // 需要新建pod
        if (podId == null) {
            podId = IdGenerator.generate();


        }

        // 更新pod分配map


        plan.setPodId(podId);
        return podId;
    }

    private void calcUrgencyLevel() {
        for (String tdn: workflowDef.getTaskDefNames()) {
            TaskDefinition td = workflowDef.getTaskDef(tdn);
            TaskExecutionPlan plan = exePlan.getTaskExecutionPlan(tdn);
            plan.setUrgencyLevel(td.getAbsoluteStartTime() + td.getTimeLimit() - td.getAbsoluteFinishTime());
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
