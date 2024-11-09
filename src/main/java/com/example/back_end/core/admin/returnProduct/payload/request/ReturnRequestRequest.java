package com.example.back_end.core.admin.returnProduct.payload.request;

import com.example.back_end.infrastructure.constant.ReturnRequestStatusType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReturnRequestRequest {

    private Long customerId;

    private Long orderId;

    private String reasonForReturn;

    private String requestAction;

    private Integer totalReturnQuantity;

    private String customerComments;

    private String staffNotes;

    private ReturnRequestStatusType returnRequestStatusId;

}
