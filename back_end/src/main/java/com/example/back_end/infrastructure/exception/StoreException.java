package com.example.back_end.infrastructure.exception;

import com.example.back_end.infrastructure.constant.ErrorCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreException extends RuntimeException {

    private final ErrorCode errorCode;

    public StoreException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

}
