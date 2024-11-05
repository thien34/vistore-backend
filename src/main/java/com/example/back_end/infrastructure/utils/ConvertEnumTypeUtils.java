package com.example.back_end.infrastructure.utils;

import com.example.back_end.infrastructure.constant.DiscountType;

public class ConvertEnumTypeUtils {

    public static DiscountType converDiscountType(Integer type) {

        return switch (type) {
            case 0 -> DiscountType.ASSIGNED_TO_ORDER_TOTAL;
            case 1 -> DiscountType.ASSIGNED_TO_PRODUCTS;
            case 2 -> DiscountType.ASSIGNED_TO_CATEGORIES;
            case 3 -> DiscountType.ASSIGNED_TO_MANUFACTURERS;
            case 4 -> DiscountType.ASSIGNED_TO_ORDER_SUBTOTAL;
            default -> throw new IllegalStateException("Unexpected value: " + type);
        };

    }
}
