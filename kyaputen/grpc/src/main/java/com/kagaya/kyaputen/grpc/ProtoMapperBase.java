package com.kagaya.kyaputen.grpc;

import com.google.protobuf.*;
import com.kagaya.kyaputen.common.metadata.tasks.*;
import com.kagaya.kyaputen.proto.TaskPb;


/**
 * @description: 实现protobuf与java对象之间转换的工具函数
 */
public abstract class ProtoMapperBase {

    public TaskPb.Task toProto(Task from) {
        TaskPb.Task.Builder to = TaskPb.Task.newBuilder();

        return to.build();
    }

    public Task fromProto(TaskPb.Task from) {
        Task to = new Task();

        return to;
    }

}
