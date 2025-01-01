package com.employeeMangement.exception;

public class EntityNotFoundException extends RuntimeException {

    // Constructor with message
    public EntityNotFoundException(String message) {
        super(message);
    }

    // Constructor with message and cause
    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}