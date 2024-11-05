package com.example.back_end.infrastructure.utils;

import com.example.back_end.infrastructure.constant.DiscountType;

public class ConvertEnumTypeUtils {
    private ConvertEnumTypeUtils() {
        throw new AssertionError("No instances allowed");
    }

    public static DiscountType converDiscountType(Integer type) {

        return switch (type) {
            case 0 -> DiscountType.ASSIGNED_TO_ORDER_TOTAL;
            case 1 -> DiscountType.ASSIGNED_TO_PRODUCTS;
            default -> throw new IllegalStateException("Unexpected value: " + type);
        };

    }
}
