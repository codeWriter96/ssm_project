package com.bytedance.crm.exception;

public class ListNotFoundException extends RuntimeException {
    public ListNotFoundException() {
        super();
    }

    public ListNotFoundException(String message) {
        super(message);
    }
}
