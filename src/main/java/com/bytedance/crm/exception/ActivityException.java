package com.bytedance.crm.exception;

public class ActivityException extends RuntimeException{
    public ActivityException() {
        super();
    }

    public ActivityException(String message) {
        super(message);
    }
}
