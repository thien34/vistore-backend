package com.example.back_end.infrastructure.utils;

import com.example.back_end.infrastructure.constant.DiscountType;

public class ConvertEnumTypeUtils {

    public static DiscountType converDiscountType(Integer type) {

        return switch (type) {
            case 0 -> DiscountType.AMOUNT;
            case 1 -> DiscountType.PERCENTAGE;
            default -> throw new IllegalStateException("Unexpected value: " + type);
        };

    }
}
