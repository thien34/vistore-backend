package com.example.back_end.infrastructure.exception;

public class CustomJsonProcessingException extends RuntimeException {
    public CustomJsonProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
