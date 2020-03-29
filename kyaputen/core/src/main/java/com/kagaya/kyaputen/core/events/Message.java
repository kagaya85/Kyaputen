package com.kagaya.kyaputen.core.events;


import java.util.Objects;

public class Message {

    public enum MessageType {
        WorkflowMessage, TaskMessage;
    }

    private String payload;

    private String id;

    private MessageType type;

    private int priority;

    public Message(String id) {
        this.id = id;
    }

    public Message(String id, int priority) {
        this.id = id;
        this.priority = priority;
    }

    public Message(String id, MessageType type, int priority) {
        this.id = id;
        this.type = type;
        this.priority = priority;
    }

    /**
     * @return the payload
     */
    public String getPayload() {
        return payload;
    }

    /**
     * @param payload the payload to set
     */
    public void setPayload(String payload) {
        this.payload = payload;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the message priority
     * @return priority of message.
     */
    public int getPriority() {
        return priority;
    }


    public void setPriority(int priority) {
        this.priority = priority;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return id;
    }


}