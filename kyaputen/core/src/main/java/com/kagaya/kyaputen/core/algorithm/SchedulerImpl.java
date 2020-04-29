package com.kagaya.kyaputen.core.algorithm;


import com.kagaya.kyaputen.common.metadata.tasks.Task;
import com.kagaya.kyaputen.common.metadata.tasks.TaskDefinition;
import com.kagaya.kyaputen.common.runtime.Workflow;
import com.kagaya.kyaputen.common.schedule.DeploymentPlan;
import com.kagaya.kyaputen.common.schedule.ExecutionPlan;
import com.kagaya.kyaputen.common.metadata.workflow.WorkflowDefinition;
import com.kagaya.kyaputen.core.config.Constant;
import com.kagaya.kyaputen.core.config.Price;
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
    public void calcWorkflowCostEfficient(WorkflowDefinition workflowDef, double deadlineFactor) {
        Map<String, Double> ce = new HashMap<>();
        Map<String, Integer> taskNum = new HashMap<>();
        Map<String, Integer> typeList = workflowDef.getTaskTypeNums();


        for (String type: typeList.keySet()) {
            ce.put(type, Constant.POD_MIN_CU);
        }

        // 原始执行时间
        long expectedExecutionTime = calcExpectedExecutionTime(workflowDef, ce);
        Map<String, Integer> taskTypeNum = workflowDef.getTaskTypeNums();

        // 调整cu，计算性价比
        while(true) {

            if (expectedExecutionTime < workflowDef.getTimeLimit())
                break;

            String speedupType = null;
            double maxGain = 0.0;

            // 调整单个cu，搜索加速比最高的任务类型
            for (String taskType: ce.keySet()) {
                double oldCU = ce.get(taskType);
                long oldExeTime = (long)Math.ceil(workflowDef.getTaskSize(taskType) / oldCU);
                double oldCost = calcTaskCost(ce.get(taskType), workflowDef.getTaskSize(taskType), taskTypeNum.get(taskType));

                if (oldCU >= Constant.MAX_CU)
                    continue;

                double newCU = oldCU + Constant.CU_INTERVAL;
                long newExeTime = (long)Math.ceil(workflowDef.getTaskSize(taskType) / newCU);
                double newCost = calcTaskCost(newCU, workflowDef.getTaskSize(taskType), taskTypeNum.get(taskType));
                ce.put(taskType, newCU);

                double gain;
                if (Math.abs(newCost - oldCost) < 1.0e-7) {
                    // 费用相等
                    gain = oldExeTime - newExeTime;
                }
                else if (newCost - oldCost < 0) {
                    // 费用减少
                    gain = oldCost - newCost;

                }
                else {
                    // 费用减少
                    gain = (oldExeTime - newExeTime) / (newCost - oldCost);

                }

            } // for end

            if (speedupType == null) {
                break;
            } else {
                double oldCU = ce.get(speedupType);
                if (oldCU < Constant.MAX_CU) {
                    ce.put(speedupType, oldCU + Constant.CU_INTERVAL);
                }
            }
        }

        workflowDef.setCEMap(ce);
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
            long executionTime = (long)Math.ceil(td.getTaskSize() / cu);
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
     * @param cu 计算资源量
     * @param taskSize 任务量
     * @param num 任务数量
     * @return
     */
    private double calcTaskCost(double cu, long taskSize, int num) {
        return Price.getNodeUnitPrice("Test_Node") * cu * Math.ceil(taskSize / cu / Constant.PRICE_INTERVAL) * num;
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
    private void divideSubDeadline(WorkflowDefinition workflowDef, long startTime) {

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