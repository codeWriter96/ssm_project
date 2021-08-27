package com.bytedance.crm.exception;

public class AuthorityException extends RuntimeException {
    public AuthorityException() {
        super();
    }

    public AuthorityException(String message) {
        super(message);
    }
}
