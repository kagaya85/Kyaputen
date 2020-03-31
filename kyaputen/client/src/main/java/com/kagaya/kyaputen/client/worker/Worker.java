package com.kagaya.kyaputen.client.worker;

import com.kagaya.kyaputen.common.metadata.tasks.Task;
import com.kagaya.kyaputen.common.metadata.tasks.TaskResult;

public interface Worker {

    String getTaskDefName();

    TaskResult execute(Task task);


}
