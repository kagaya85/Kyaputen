package com.kagaya.kyaputen.core.algorithm;


import com.kagaya.kyaputen.common.metadata.tasks.Task;
import com.kagaya.kyaputen.common.metadata.tasks.TaskDefinition;
import com.kagaya.kyaputen.common.runtime.Workflow;
import com.kagaya.kyaputen.common.schedule.DeploymentPlan;
import com.kagaya.kyaputen.common.schedule.ExecutionPlan;
import com.kagaya.kyaputen.common.metadata.workflow.WorkflowDefinition;
import com.kagaya.kyaputen.core.config.Constant;
import com.kagaya.kyaputen.core.execution.WorkflowExecutor;
import com.kagaya.kyaputen.core.metrics.Monitor;
import com.kagaya.kyaputen.core.service.KubernetesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class SchedulerImpl implements Scheduler {

    private static final Logger logger = LoggerFactory.getLogger(SchedulerImpl.class);

    /**
     * 计算工作流中每个任务的资源需求量CE，写入工作流定义
     * @param workflowDef - 工作流定义
     * @param deadlineFactor - 最后期限管理严格成都
     */
    @Override
    public void calcWorkflowResource(WorkflowDefinition workflowDef, double deadlineFactor) {
        Map<String, Double> ce = new HashMap<>();
        Map<String, Integer> taskNum = new HashMap<>();
        Map<String, Integer> typeList = workflowDef.getTaskTypeNums();


        for (String type: typeList.keySet()) {
            ce.put(type, Constant.POD_MIN_CU);
        }

        // 调整cu，计算性价比
        while(true) {
            long oldExpectedTime = calcExpectedExecutionTime(workflowDef, ce);


        }


    }

    /**
     * 计算工作流的期望执行时间，同时更新每个任务定义的期望开始时间与期望完成时间
     * @param workflowDef
     * @param ce
     * @return
     */
    private long calcExpectedExecutionTime(WorkflowDefinition workflowDef, Map<String, Double> ce) {

        long expectedExecutionTime = 0;
        List<String> taskQueue = new LinkedList<>();
        TaskDefinition td = workflowDef.getStartTaskDefinition();
        taskQueue.add(td.getTaskDefName());

        // BFS
        while (!taskQueue.isEmpty()) {
            td = workflowDef.getTaskDef(taskQueue.get(0));
            taskQueue.remove(0);
            List<String> priorTasks = td.getPriorTasks();

            long arrivalTime = 0;
            long expectedStartTime = 0;
            for (String taskName: priorTasks) {
                arrivalTime = workflowDef.getTaskDef(taskName).getExpectedFinishTime();
                arrivalTime += Monitor.getTaskRecentLatencyTime(workflowDef.getTaskDef(taskName).getTaskType());
                expectedStartTime = Math.max(expectedStartTime, arrivalTime);
            }

            double cu = ce.get(td.getTaskType());
            long executionTime = (long)(td.getTaskSize() / cu);
            long finishTime = expectedStartTime + executionTime;
            td.setExpectedFinishTime(finishTime);
            expectedExecutionTime = Math.max(expectedExecutionTime, finishTime);

            for (String tn: td.getNextTasks()) {
                if (!taskQueue.contains(tn))
                    taskQueue.add(tn);
            }
        }

        logger.debug("Calculate Expected Execution Time of workflow: {}", workflowDef.getName());
        return expectedExecutionTime;
    }


    /**
     * 计算任务成本
     * @param vmType
     * @param ecu
     * @param taskSize
     * @return
     */
    private double calcTaskCost(String vmType, double ecu, double taskSize) {

        return 0.0;
    }

    /**
     * 计算工作流成本
     * @return 工作流总成本
     */
    private double calcWorkflowCost() {
        double cost = 0;

        return cost;
    }

    /**
     * 划分子截止时间
     * @param workflowDef 工作流定义
     * @param startTime 工作流计划开始时间
     */
    private void devideSubDeadline(WorkflowDefinition workflowDef, long startTime) {

    }

    /**
     * 生成执行方案
     */
    public ExecutionPlan genExecutionPlan() {

        ExecutionPlan plan = new ExecutionPlan();


        return plan;
    }

    /**
     * 生成部署方案
     */
    public DeploymentPlan genDeploymentPlan() {

        DeploymentPlan plan = new DeploymentPlan();


        return plan;
    }

    /**
     * 应用调度方案，包括申请资源
     */
    public void apply(ExecutionPlan executionPlan, DeploymentPlan deploymentPlan) {

        String apiServerAddr = "";
        String token = "";

        KubernetesService k8s = new KubernetesService(apiServerAddr, token);


    }

}