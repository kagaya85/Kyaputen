package com.kagaya.kyaputen.client.entity;

import com.kagaya.kyaputen.client.config.KyaputenClientConfig;
import com.kagaya.kyaputen.client.config.DefaultKyaputenClientConfig;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.List;

public class TaskClient extends ClientBase {
    private List taskList;
    private List pollDataList;

    public static final Logger logger = LoggerFactory.getLogger(TaskClient.class);

    public TaskClient() {
        this(new DefaultKyaputenClientConfig());
    }

    public TaskClient(KyaputenClientConfig config) {
        super(config);
    }

}
