package com.example.back_end.core.admin.order.payload;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderItemSummary {
    private Long orderItemId;
    private Long productId;
    private String productName;
    private Long quantity;
    private BigDecimal productPrice;
    private BigDecimal discountAmount;
}
