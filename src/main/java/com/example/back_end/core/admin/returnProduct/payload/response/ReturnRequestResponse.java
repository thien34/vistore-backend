package com.example.back_end.core.admin.returnProduct.payload.response;

import com.example.back_end.infrastructure.constant.ReturnRequestStatusType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ReturnRequestResponse {
    private Long id;

    private String firstName;

    private String lastName;

    private Long orderId;

    private String reasonForReturn;

    private String requestAction;

    private Integer totalReturnQuantity;

    private String customerComments;

    private BigDecimal returnFee;

    private String staffNotes;

    private ReturnRequestStatusType returnRequestStatusId;
}
