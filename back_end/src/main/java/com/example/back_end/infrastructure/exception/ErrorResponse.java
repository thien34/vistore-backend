package com.example.back_end.infrastructure.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class ErrorResponse {

    private Date timestamp;

    private int status;

    private String path;

    private String error;

    private String message;
}
