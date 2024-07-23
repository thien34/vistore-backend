package com.example.back_end.infrastructure.exception;

public class NotExistsException extends RuntimeException {
    public NotExistsException(String message) {
        super(message);
    }

    public NotExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
