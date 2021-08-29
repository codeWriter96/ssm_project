package com.bytedance.crm.exception;

public class UpdateException extends RuntimeException {
    public UpdateException() {
        super();
    }

    public UpdateException(String message) {
        super(message);
    }
}
