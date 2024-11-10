package com.example.back_end.infrastructure.constant;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum PaymentMethodType {
    CASH(0),
    TRANSFER(1);

    public final int value;
}
