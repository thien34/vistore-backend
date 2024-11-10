package com.example.back_end.core.admin.returnProduct.payload.response;

import com.example.back_end.infrastructure.constant.ReturnRequestStatusType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReturnRequestResponse {
    private Long id;

    private Long customerId;

    private Long orderId;

    private String reasonForReturn;

    private String requestAction;

    private Integer totalReturnQuantity;

    private String customerComments;

    private String staffNotes;

    private ReturnRequestStatusType returnRequestStatusId;
}
