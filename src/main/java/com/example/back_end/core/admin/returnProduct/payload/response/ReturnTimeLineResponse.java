package com.example.back_end.core.admin.returnProduct.payload.response;

import com.example.back_end.infrastructure.constant.ReturnRequestStatusType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class ReturnTimeLineResponse {
    private  Long returnRequestId;
    private ReturnRequestStatusType status;
    private String description;
    private LocalDateTime createdDate;
}
