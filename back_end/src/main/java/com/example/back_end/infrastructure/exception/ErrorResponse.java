package com.example.back_end.infrastructure.exception;


import lombok.Data;

import java.util.Date;

@Data
public class ErrorResponse {
    private Date timestamp;
    private int status;
    private String path;
    private String error;
    private String message;
}
