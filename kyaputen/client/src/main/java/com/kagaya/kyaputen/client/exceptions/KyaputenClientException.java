package com.kagaya.kyaputen.client.exceptions;

public class KyaputenClientException extends RuntimeException{
    private int status;
    private String message;
    private String instance;
    private String code;
    private boolean retriable;

    public KyaputenClientException() { super(); }

    public KyaputenClientException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }

    public KyaputenClientException(int status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append(getClass().getName()).append(": ");

        if (this.message != null) {
            builder.append(message);
        }

        if (status > 0) {
            builder.append(" {status=").append(status);
            if (this.code != null) {
                builder.append(", code='").append(code).append("'");
            }

            builder.append(", retriable: ").append(retriable);
        }

        if (this.instance != null) {
            builder.append(", instance: ").append(instance);
        }

        builder.append("}");
        return builder.toString();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isRetriable() {
        return retriable;
    }

    public void setRetriable(boolean retriable) {
        this.retriable = retriable;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }
}
