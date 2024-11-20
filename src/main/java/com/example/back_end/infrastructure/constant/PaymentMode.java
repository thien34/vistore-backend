package com.example.back_end.infrastructure.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PaymentMode {
    ONLINE(1),
    IN_STORE(2);
    public final int value;
}
