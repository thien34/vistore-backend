package com.example.back_end.core.admin.returnProduct.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReturnInvoiceRequest {

    private  Long returnRequestId;

    private Long orderId;

    private BigDecimal refundAmount;
}
