package com.kagaya.kyaputen.core.utils;

import com.kagaya.kyaputen.common.metadata.tasks.Task;
import org.apache.commons.lang3.StringUtils;


public class QueueUtils {
    public static final String DOMAIN_SEPARATOR = ":";
    public static final String EXECUTION_NAME_SPACE_SEPRATOR = "@";

    public static String getQueueName(Task task) {
        return getQueueName(task.getTaskType(), task.getDomain(), task.getExecutionNameSpace());
    }

    public static String getQueueName(String taskType, String domain, String executionNameSpace) {

        String queueName = null;
        if (domain == null) {
            queueName = taskType;
        } else {
            queueName = domain + DOMAIN_SEPARATOR + taskType;
        }

        if (executionNameSpace != null) {
            queueName = queueName + EXECUTION_NAME_SPACE_SEPRATOR + executionNameSpace;
        }

        return queueName;
    }

    public static String getQueueNameWithoutDomain(String queueName) {
        return queueName.substring(queueName.indexOf(DOMAIN_SEPARATOR) + 1);
    }

    public static String getExecutionNameSpace(String queueName) {
        if (StringUtils.contains(queueName, EXECUTION_NAME_SPACE_SEPRATOR)) {
            return StringUtils.substringAfter(queueName, EXECUTION_NAME_SPACE_SEPRATOR);
        } else {
            return StringUtils.EMPTY;
        }
    }

    public static String getTaskType(String queue) {

        if(StringUtils.isBlank(queue)) {
            return StringUtils.EMPTY;
        }

        int domainSeperatorIndex = StringUtils.indexOf(queue, DOMAIN_SEPARATOR);
        int startIndex = 0;
        if (domainSeperatorIndex == -1) {
            startIndex = 0;
        } else {
            startIndex = domainSeperatorIndex +1 ;
        }
        int endIndex = StringUtils.indexOf(queue, EXECUTION_NAME_SPACE_SEPRATOR);

        if (endIndex == -1) {
            endIndex = queue.length();
        }

        return StringUtils.substring(queue, startIndex, endIndex);
    }
}


