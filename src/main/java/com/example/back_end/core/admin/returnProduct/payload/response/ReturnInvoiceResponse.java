package com.example.back_end.core.admin.returnProduct.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ReturnInvoiceResponse {

    private Long id;

    private String billCode;

    private Long returnRequestId;

    private String firstName;

    private String lastName;

    private String customerEmail;

    private Long orderId;

    private BigDecimal returnFee;

    private BigDecimal refundAmount;

    private List<ReturnItemResponse> returnItems;
}
