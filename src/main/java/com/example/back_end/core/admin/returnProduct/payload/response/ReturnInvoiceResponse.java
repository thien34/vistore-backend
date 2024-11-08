package com.example.back_end.core.admin.returnProduct.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ReturnInvoiceResponse {

    private Long id;

    private  Long returnRequestId;

    private Long orderId;

    private BigDecimal refundAmount;
}
