package com.example.back_end.core.admin.returnProduct.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReturnItemRequest {

    private Long orderItemId;

    private Long returnRequestId;

    private Long productId;

    private Integer quantity;

    private BigDecimal oldUnitPrice;

    private BigDecimal discountAmountPerItem;

    private BigDecimal refundTotal;
}
