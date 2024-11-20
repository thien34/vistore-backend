package com.example.back_end.infrastructure.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PaymentMethodType {
    CASH(0),
    TRANSFER(1),
    COD(2);

    public final int value;
}
