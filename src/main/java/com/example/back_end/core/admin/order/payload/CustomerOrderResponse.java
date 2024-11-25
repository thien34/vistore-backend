package com.example.back_end.core.admin.order.payload;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CustomerOrderResponse {
    private Long orderId;
    private Long customerId;
    private String firstName;
    private String lastName;
    private BigDecimal orderTotal;
}
