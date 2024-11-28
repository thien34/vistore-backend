package com.example.back_end.core.admin.returnProduct.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ReturnItemResponse {

    private Long id;

    private String productName;

    private Long productId;

    private Integer quantity;

    private BigDecimal oldUnitPrice;

    private BigDecimal discountAmountPerItem;

    private BigDecimal refundTotal;
}
