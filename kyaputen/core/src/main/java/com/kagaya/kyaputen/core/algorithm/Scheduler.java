package com.kagaya.kyaputen.core.algorithm;

import com.kagaya.kyaputen.common.metadata.workflow.WorkflowDefinition;
import com.kagaya.kyaputen.common.schedule.ExecutionPlan;


public interface Scheduler {

    /**
     * 计算ce，保存在工作流定义中
     * @param workflowDef
     * @param deadlineFactor
     */
    void calcWorkflowCostEfficient(WorkflowDefinition workflowDef, double deadlineFactor);

    /**
     * 遍历工作流所需的pod，对不符合ce配置的pod做update标记
     * @param workflowDef
     */
    void markUpdatePod(WorkflowDefinition workflowDef);

    /**
     * 划分截止时间
     * @param workflowDef
     * @param startTime
     * @param deadline
     */
    void divideSubDeadline(WorkflowDefinition workflowDef, long startTime, long deadline);

    /**
     *
     * @param startTime
     * @param workflowDef
     * @return
     */
    ExecutionPlan genExecutionPlan(long startTime, WorkflowDefinition workflowDef);

    /**
     * 应用部署
     * @param workflowDef
     * @param executionPlan
     */
    void deploy(WorkflowDefinition workflowDef, ExecutionPlan executionPlan);
}