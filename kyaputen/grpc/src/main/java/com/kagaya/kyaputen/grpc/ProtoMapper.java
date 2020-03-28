package com.kagaya.kyaputen.grpc;

import com.google.protobuf.*;
import com.kagaya.kyaputen.common.metadata.tasks.Task;
import com.kagaya.kyaputen.proto.TaskPb;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

public class ProtoMapper {

    public Value toProto(Object val) {
        Value.Builder builder = Value.newBuilder();

        if (val == null) {
            builder.setNullValue(NullValue.NULL_VALUE);
        } else if (val instanceof Boolean) {
            builder.setBoolValue((Boolean) val);
        } else if (val instanceof Double) {
            builder.setNumberValue((Double) val);
        } else if (val instanceof String) {
            builder.setStringValue((String) val);
        } else if (val instanceof Map) {
            Map<String, Object> map = (Map<String, Object>) val;
            Struct.Builder struct = Struct.newBuilder();
            for (Map.Entry<String, Object> pair : map.entrySet()) {
                struct.putFields(pair.getKey(), toProto(pair.getValue()));
            }
            builder.setStructValue(struct.build());
        } else if (val instanceof List) {
            ListValue.Builder list = ListValue.newBuilder();
            for (Object obj : (List<Object>)val) {
                list.addValues(toProto(obj));
            }
            builder.setListValue(list.build());
        } else {
            throw new ClassCastException("cannot map to Value type: "+ val);
        }
        return builder.build();
    }

    public Object fromProto(Value any) {
        switch (any.getKindCase()) {
            case NULL_VALUE:
                return null;
            case BOOL_VALUE:
                return any.getBoolValue();
            case NUMBER_VALUE:
                return any.getNumberValue();
            case STRING_VALUE:
                return any.getStringValue();
            case STRUCT_VALUE:
                Struct struct = any.getStructValue();
                Map<String, Object> map = new HashMap<>();
                for (Map.Entry<String, Value> pair : struct.getFieldsMap().entrySet()) {
                    map.put(pair.getKey(), fromProto(pair.getValue()));
                }
                return map;
            case LIST_VALUE:
                List<Object> list = new ArrayList<>();
                for (Value val : any.getListValue().getValuesList()) {
                    list.add(fromProto(val));
                }
                return list;
            default:
                throw new ClassCastException("unset Value element: "+ any);
        }
    }

//        public PollDataPb.PollData toProto(PollData from) {
//        PollDataPb.PollData.Builder to = PollDataPb.PollData.newBuilder();
//        if (from.getQueueName() != null) {
//            to.setQueueName( from.getQueueName() );
//        }
//        if (from.getDomain() != null) {
//            to.setDomain( from.getDomain() );
//        }
//        if (from.getWorkerId() != null) {
//            to.setWorkerId( from.getWorkerId() );
//        }
//        to.setLastPollTime( from.getLastPollTime() );
//        return to.build();
//    }
//
//    public PollData fromProto(PollDataPb.PollData from) {
//        PollData to = new PollData();
//        to.setQueueName( from.getQueueName() );
//        to.setDomain( from.getDomain() );
//        to.setWorkerId( from.getWorkerId() );
//        to.setLastPollTime( from.getLastPollTime() );
//        return to;
//    }

    public TaskPb.Task toProto(Task from) {

        TaskPb.Task.Builder to = TaskPb.Task.newBuilder();
        if (from.getTaskId() != null) {
            to.setTaskId( from.getTaskId() );
        }
        if (from.getTaskType() != null) {
            to.setTaskType( from.getTaskType() );
        }
        if (from.getStatus() != null) {
            to.setStatus( toProto( from.getStatus() ) );
        }
        if (from.getReferenceTaskName() != null) {
            to.setReferenceTaskName( from.getReferenceTaskName() );
        }
        to.setRetryCount( from.getRetryCount() );
        to.setSeq( from.getSeq() );
        to.setPollCount( from.getPollCount() );
        to.setScheduledTime( from.getScheduledTime() );
        to.setStartTime( from.getStartTime() );
        to.setEndTime( from.getEndTime() );
        to.setUpdateTime( from.getUpdateTime() );
        to.setStartDelayInSeconds( from.getStartDelayInSeconds() );
        to.setRetried( from.isRetried() );
        to.setExecuted( from.isExecuted() );
        if (from.getWorkerId() != null) {
            to.setWorkerId( from.getWorkerId() );
        }
        if (from.getDomain() != null) {
            to.setDomain( from.getDomain() );
        }
        if (from.getExecutionNameSpace() != null) {
            to.setExecutionNameSpace( from.getExecutionNameSpace() );
        }
        for (Map.Entry<String, Object> pair : from.getInputData().entrySet()) {
            to.putInputData( pair.getKey(), toProto( pair.getValue() ) );
        }
        for (Map.Entry<String, Object> pair : from.getOutputData().entrySet()) {
            to.putOutputData( pair.getKey(), toProto( pair.getValue() ) );
        }
        to.setWorkflowPriority( from.getWorkflowPriority() );
        if (from.getSubWorkflowId() != null) {
            to.setSubWorkflowId( from.getSubWorkflowId() );
        }
        return to.build();
    }

    public Task fromProto(TaskPb.Task from) {

        Task to = new Task();
        to.setTaskId( from.getTaskId() );
        to.setTaskType( from.getTaskType() );
        to.setStatus( fromProto( from.getStatus() ) );
        to.setReferenceTaskName( from.getReferenceTaskName() );
        to.setRetryCount( from.getRetryCount() );
        to.setSeq( from.getSeq() );
        to.setPollCount( from.getPollCount() );
        to.setScheduledTime( from.getScheduledTime() );
        to.setStartTime( from.getStartTime() );
        to.setEndTime( from.getEndTime() );
        to.setUpdateTime( from.getUpdateTime() );
        to.setStartDelayInSeconds( from.getStartDelayInSeconds() );
        to.setRetried( from.getRetried() );
        to.setExecuted( from.getExecuted() );
        to.setWorkerId( from.getWorkerId() );
        to.setDomain( from.getDomain() );
        to.setExecutionNameSpace( from.getExecutionNameSpace() );
        Map<String, Object> inputDataMap = new HashMap<String, Object>();
        for (Map.Entry<String, Value> pair : from.getInputDataMap().entrySet()) {
            inputDataMap.put( pair.getKey(), fromProto( pair.getValue() ) );
        }
        to.setInputData(inputDataMap);
        Map<String, Object> outputDataMap = new HashMap<String, Object>();
        for (Map.Entry<String, Value> pair : from.getOutputDataMap().entrySet()) {
            outputDataMap.put( pair.getKey(), fromProto( pair.getValue() ) );
        }
        to.setOutputData(outputDataMap);
        to.setWorkflowInstanceId( from.getWorkflowInstanceId() );
        to.setWorkflowPriority( from.getWorkflowPriority() );
        to.setSubWorkflowId( from.getSubWorkflowId() );

        return to;
    }

    public TaskPb.Task.Status toProto(Task.Status from) {
        TaskPb.Task.Status to;
        switch (from) {
            case IN_PROGRESS: to = TaskPb.Task.Status.IN_PROGRESS; break;
            case CANCELED: to = TaskPb.Task.Status.CANCELED; break;
            case FAILED: to = TaskPb.Task.Status.FAILED; break;
            case FAILED_WITH_TERMINAL_ERROR: to = TaskPb.Task.Status.FAILED_WITH_TERMINAL_ERROR; break;
            case COMPLETED: to = TaskPb.Task.Status.COMPLETED; break;
            case COMPLETED_WITH_ERRORS: to = TaskPb.Task.Status.COMPLETED_WITH_ERRORS; break;
            case SCHEDULED: to = TaskPb.Task.Status.SCHEDULED; break;
            case TIMED_OUT: to = TaskPb.Task.Status.TIMED_OUT; break;
            case SKIPPED: to = TaskPb.Task.Status.SKIPPED; break;
            default: throw new IllegalArgumentException("Unexpected enum constant: " + from);
        }
        return to;
    }

    public Task.Status fromProto(TaskPb.Task.Status from) {
        Task.Status to;
        switch (from) {
            case IN_PROGRESS: to = Task.Status.IN_PROGRESS; break;
            case CANCELED: to = Task.Status.CANCELED; break;
            case FAILED: to = Task.Status.FAILED; break;
            case FAILED_WITH_TERMINAL_ERROR: to = Task.Status.FAILED_WITH_TERMINAL_ERROR; break;
            case COMPLETED: to = Task.Status.COMPLETED; break;
            case COMPLETED_WITH_ERRORS: to = Task.Status.COMPLETED_WITH_ERRORS; break;
            case SCHEDULED: to = Task.Status.SCHEDULED; break;
            case TIMED_OUT: to = Task.Status.TIMED_OUT; break;
            case SKIPPED: to = Task.Status.SKIPPED; break;
            default: throw new IllegalArgumentException("Unexpected enum constant: " + from);
        }
        return to;
    }

}
