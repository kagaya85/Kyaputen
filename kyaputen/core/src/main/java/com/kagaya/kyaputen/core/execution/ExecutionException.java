package com.kagaya.kyaputen.core.execution;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class ExecutionException extends RuntimeException {

    public enum Code {

        INVALID_INPUT(400), INTERNAL_ERROR(500), NOT_FOUND(404), CONFLICT(409), UNAUTHORIZED(403), BACKEND_ERROR(500);

        private int statusCode;

        Code(int statusCode) {
            this.statusCode = statusCode;
        }

        public int getStatusCode() {
            return statusCode;
        }
    }

    private Code code;

    public boolean isRetriable() {
        return this.code == Code.BACKEND_ERROR;
    }

    public ExecutionException(String msg, Throwable t){
        this(Code.INTERNAL_ERROR, msg, t);
    }

    public ExecutionException(Code code, String msg, Throwable t){
        super(code + " - " + msg, t);
        this.code = code;
    }

    public ExecutionException(Code code, Throwable t){
        super(code.name(), t);
        this.code = code;
    }

    public ExecutionException(Code code, String message){
        super(message);
        this.code = code;
    }

    public int getHttpStatusCode(){
        return this.code.getStatusCode();
    }

    public Code getCode(){
        return this.code;
    }

    public String getTrace(){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        this.printStackTrace(ps);
        ps.flush();
        return new String(baos.toByteArray());
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> map = new LinkedHashMap<>();
        map.put("code", code.name());
        map.put("message", super.getMessage());
        map.put("retryable", isRetriable());
        return map;
    }

}
