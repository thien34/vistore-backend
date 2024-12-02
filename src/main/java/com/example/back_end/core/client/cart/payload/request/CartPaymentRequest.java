package com.example.back_end.core.client.cart.payload.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CartPaymentRequest {

    private List<Long> idCarts;

}
