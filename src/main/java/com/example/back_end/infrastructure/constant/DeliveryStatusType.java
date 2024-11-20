package com.example.back_end.infrastructure.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DeliveryStatusType {
    DELIVERED(0),
    NOT_DELIVERED(1);
    public final int value;
}
