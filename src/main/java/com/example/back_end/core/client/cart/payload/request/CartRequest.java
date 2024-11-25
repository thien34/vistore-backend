package com.example.back_end.core.client.cart.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartRequest {

    private Long productId;

    private Integer quantity;

    private Long customerId;

}
