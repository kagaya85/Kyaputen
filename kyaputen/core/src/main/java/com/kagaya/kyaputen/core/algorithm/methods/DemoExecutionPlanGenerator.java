package com.kagaya.kyaputen.core.algorithm.methods;

import com.kagaya.kyaputen.common.metadata.tasks.TaskDefinition;
import com.kagaya.kyaputen.common.metadata.workflow.WorkflowDefinition;
import com.kagaya.kyaputen.common.runtime.Node;
import com.kagaya.kyaputen.common.runtime.Pod;
import com.kagaya.kyaputen.common.schedule.ExecutionPlan;
import com.kagaya.kyaputen.common.schedule.TaskExecutionPlan;
import com.kagaya.kyaputen.core.algorithm.SchedulerImpl;
import com.kagaya.kyaputen.core.config.Constant;
import com.kagaya.kyaputen.core.config.PullTime;
import com.kagaya.kyaputen.core.dao.NodeResourceDAO;
import com.kagaya.kyaputen.core.dao.PodResourceDAO;
import com.kagaya.kyaputen.core.utils.IdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class DemoExecutionPlanGenerator implements Method {

    private static final Logger logger = LoggerFactory.getLogger(DemoExecutionPlanGenerator.class);

    private ExecutionPlan exePlan = null;

    private WorkflowDefinition workflowDef = null;

    private PodResourceDAO podResource = new PodResourceDAO();

    PodResourceDAO podResourceDAO = new PodResourceDAO();

    NodeResourceDAO nodeResourceDAO = new NodeResourceDAO();

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
                Node selectedNode = null;
                for (Node node: nodeResourceDAO.getNodeList()) {
                    if (node.getStatus().isDown())
                        continue;

                    double remainComputeUnit = node.getRemainComputeUnit();

                    if (remainComputeUnit + Constant.E > pod.getComputeUnit()) {
                        selectedNode = node;
                        break;
                    }
                }

                if (selectedNode == null) {
                    // 部署阶段创建新node
                    logger.info("No suitable Node for Pod: {}, Id: {}, need to create a new pod", pod.getTaskImageName(), pod.getPodId());
                }

                double price = selectedNode.getPrice() * (pod.getComputeUnit()/selectedNode.getTotalComputeUnit());
                pod.setPrice(price);
                pod.setNodeId(selectedNode.getId());
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
        long executionTime;
        int minPrice = Integer.MAX_VALUE;
        long deadline = startTime + taskDef.getAbsoluteStartTime() + taskDef.getTimeLimit();

        for (String pid: podResource.getPodIdList()) {
            Pod pod = podResource.getPod(pid);

            // 镜像类型不符合或者已经结束的Pod
            if (!pod.getTaskImageName().equals(plan.getTaskType()) || pod.getStatus().equals(Pod.PodStatus.DOWN) || pod.getStatus().equals(Pod.PodStatus.ERROR))
                continue;

            executionTime = (long)Math.ceil(taskDef.getTaskSize() / pod.getComputeUnit());

            if (pod.getEarliestStartTime() + executionTime < deadline) {
                // 满足时间需求的情况下，选择最优pod
                if(pod.getPrice() < minPrice)
                    podId = pid;
            }

        }

        // 需要新建pod
        if (podId == null) {
            long est = Constant.POD_LUNCH_TIME_MS + PullTime.getPullTime(plan.getTaskType());
            podId = IdGenerator.generate();

            Pod pod = new Pod();
            pod.setPodId(podId);
            pod.setStatus(Pod.PodStatus.NEW);
            pod.addPullImageTask(est);
            pod.setTaskImageName(plan.getTaskType());

            // 性价比最高的ce
            double bestFitCe = workflowDef.getCeByType(plan.getTaskType());
            // 最小ce
            double minCe = (long)Math.ceil(taskDef.getTaskSize() / (deadline - est));

            if (minCe < bestFitCe) {
                pod.setComputeUnit(minCe);
            }
            else {
                pod.setComputeUnit(bestFitCe);
            }

            // 更新pod分配map
            podResource.addPod(pod);
        }

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
