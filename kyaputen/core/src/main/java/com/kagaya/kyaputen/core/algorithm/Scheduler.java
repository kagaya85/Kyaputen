package com.kagaya.kyaputen.core.algorithm;

import com.kagaya.kyaputen.core.metadata.DeploymentPlan;
import com.kagaya.kyaputen.core.metadata.ExecutionPlan;
import com.kagaya.kyaputen.common.metadata.workflow.WorkflowDefinition;
import com.kagaya.kyaputen.core.service.KubernetesService;

public class Scheduler implements SchedulerBase {

    public Scheduler() {

    }

    /**
     * 计算工作流中每个任务的资源需求量
     * @param workflowDef - 工作流定义
     * @param deadlineFactor - 最后期限管理严格成都
     */
    @Override
    public void calcResource(WorkflowDefinition workflowDef, double deadlineFactor) {

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

        KubernetesService k8s = new KubernetesService();
    }


}