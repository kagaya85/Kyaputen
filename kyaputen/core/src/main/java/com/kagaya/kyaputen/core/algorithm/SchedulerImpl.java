package com.kagaya.kyaputen.core.algorithm;


import com.kagaya.kyaputen.common.schedule.DeploymentPlan;
import com.kagaya.kyaputen.common.schedule.ExecutionPlan;
import com.kagaya.kyaputen.common.metadata.workflow.WorkflowDefinition;
import com.kagaya.kyaputen.common.schedule.PodResource;
import com.kagaya.kyaputen.core.service.KubernetesService;
import com.kagaya.kyaputen.core.metrics.Monitor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SchedulerImpl implements Scheduler {


    /**
     * 计算工作流中每个任务的资源需求量CE，写入工作流定义
     * @param workflowDef - 工作流定义
     * @param deadlineFactor - 最后期限管理严格成都
     */
    @Override
    public void calcWorkflowResource(WorkflowDefinition workflowDef, double deadlineFactor) {
        Map<String, PodResource> ce = new HashMap<>();
        Map<String, Integer> taskNum = new HashMap<>();

        Map<String, Integer> typeList = workflowDef.getTaskTypeNums();



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
     * @return
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