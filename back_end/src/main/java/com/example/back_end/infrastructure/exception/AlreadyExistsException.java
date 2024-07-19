package com.example.back_end.infrastructure.exception;

public class AlreadyExistsException extends RuntimeException {

    // Constructor với thông điệp lỗi
    public AlreadyExistsException(String message) {
        super(message);
    }

    // Constructor với thông điệp lỗi và nguyên nhân (cause)
    public AlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
