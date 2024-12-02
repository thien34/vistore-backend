package com.example.back_end.core.admin.returnProduct.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PendingReturnItemResponse {

    private Long id;

    private Long returnRequestId;

    private String productJson;

    private Integer quantity;

    private LocalDateTime createdDate;
}