package com.bytedance.crm.exception;

public class AddException extends RuntimeException{
    public AddException() {
        super();
    }

    public AddException(String message) {
        super(message);
    }
}
