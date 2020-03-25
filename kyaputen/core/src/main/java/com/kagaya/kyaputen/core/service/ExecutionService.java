package com.kagaya.kyaputen.core.service;

import com.kagaya.kyaputen.core.dao.QueueDAO;
import com.kagaya.kyaputen.common.metadata.tasks.Task;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @description 逻辑部分代码
 */

@Singleton
public class ExecutionService {

    private static final Logger logger = LoggerFactory.getLogger(ExecutionService.class);

    private final QueueDAO queueDAO;

    private static final int MAX_POLL_TIMEOUT_MS = 5000;
    private static final int POLL_COUNT_ONE = 1;
    private static final int POLLING_TIMEOUT_IN_MS = 100;

    private static final int MAX_SEARCH_SIZE = 5_000;

    @Inject
    public ExecutionService(QueueDAO queueDAO) {
        this.queueDAO = queueDAO;
    }

    public Task poll(String taskType, String workId) {
        return poll(taskType, workId, null);
    }

    public Task poll(String taskType, String workerId, String domain) {
        List<Task> tasks = poll(taskType, workerId, domain, i, 100);
        
        if (tasks.isEmpty()) {
            return null;
        
        }
        else
            reutrn tasks.get(0);
    }

    public Task poll(String taskType, String workerId, String domain, int count, int timeoutMilliSecond) {

    }
}
