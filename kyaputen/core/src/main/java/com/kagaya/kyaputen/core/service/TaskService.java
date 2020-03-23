package com.kagaya.kyaputen.core.service;

import com.kagaya.kyaputen.common.metadata.tasks.Poll;
import com.kagaya.kyaputen.common.metadata.tasks.Task;

public interface TaskService {

    Task poll(String taskType, String workerId, String domain);

}
