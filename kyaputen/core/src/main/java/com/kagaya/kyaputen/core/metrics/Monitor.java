package com.kagaya.kyaputen.core.metrics;

import com.kagaya.kyaputen.common.metadata.tasks.Task;
import com.kagaya.kyaputen.common.metadata.tasks.TaskDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class Monitor {

    private static Map<String, Long> taskExecutionTimeMap = new HashMap<>();

    private static Map<String, Integer> taskExecutionLogNumMap = new HashMap<>();

    private static Map<String, Long> nodeLatencyTimeMap = new HashMap<>();

    // 记录某种任务类型最近在那个node上执行
    private static Map<String, String> taskRecentExecutionNodeMap = new HashMap<>();

    public static final Logger logger = LoggerFactory.getLogger(Monitor.class);

    /**
     * 记录任务执行时间的平均值，用于代表任务大小
     * @param taskType 任务类型
     * @param timeMs 任务执行时间 单位ms
     */
    public static void logTaskExecutionTime(String taskType, long timeMs) {
        Long t = taskExecutionTimeMap.get(taskType);

        if (t == null) {
            t = timeMs;
            taskExecutionLogNumMap.put(taskType, 1);
        } else {
            int num = taskExecutionLogNumMap.get(taskType);
            t = t * num;
            num++;
            t = (t + timeMs) / num;
            taskExecutionLogNumMap.put(taskType, num);
        }

        taskExecutionTimeMap.put(taskType, t);
        logger.debug("Log taskType: {}, execution time: {}", taskType, timeMs);
    }

    public static long getTaskExecutionTime(String taskType) {
        Long time = taskExecutionTimeMap.get(taskType);

        if (time == null) {
            time = 5000L;
        }

        return time;
    }

    public static void resetTaskExecutionTime(String taskType) {
        taskExecutionTimeMap.remove(taskType);
    }

    /**
     * 记录node节点网络延时的最新值
     * @param nodeId Id
     * @param timeMs 延时时长 单位ms
     */
    private static void logNodeLatencyTime(String nodeId, long timeMs) {
        nodeLatencyTimeMap.put(nodeId, timeMs);
    }

    public static long getNodeLatencyTime(String nodeId) {
        Long time = nodeLatencyTimeMap.get(nodeId);

        if (time == null) {
            time = 0L;
        }

        return time;
    }

    public static void logTaskLatencyTime(Task task, long timeMs) {
        String nodeId = task.getNodeId();
        taskRecentExecutionNodeMap.put(task.getTaskType(), nodeId);
        logNodeLatencyTime(nodeId, timeMs);
        logger.debug("Log nodeId: {}, latency time: {}", nodeId, timeMs);
    }

    /**
     * 获取某个类型任务的最新延迟信息
     * @param taskType
     * @return mstime if exist, or 0
     */
    public static long getTaskRecentLatencyTime(String taskType) {
        String nodeId = taskRecentExecutionNodeMap.get(taskType);

        if (nodeId == null)
            return 0;

        Long time = nodeLatencyTimeMap.get(nodeId);

        if (time == null)
            return 0;

        return time;

    }

}
