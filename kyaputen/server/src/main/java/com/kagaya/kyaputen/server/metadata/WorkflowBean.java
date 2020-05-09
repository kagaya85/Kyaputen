package com.kagaya.kyaputen.server.metadata;

import java.util.List;

public class WorkflowBean {

    public String name;

    public String description;

    public String version;

    public List<TaskInfo> tasks;

    public long timeLimit;

    public class TaskInfo {

        public String taskName;

        public String taskType;

        public String description;

        public int timeoutSeconds;

        public List<String> inputKeys;

        public List<String> outputKeys;

        public List<String> priorTasks;

        public List<String> nextTasks;
    }
}
