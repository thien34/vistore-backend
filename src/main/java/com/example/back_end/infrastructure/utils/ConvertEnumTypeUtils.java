package com.example.back_end.infrastructure.utils;

import com.example.back_end.infrastructure.constant.DiscountType;

public class ConvertEnumTypeUtils {
    private ConvertEnumTypeUtils() {
        throw new AssertionError("Không được phép sử dụng");
    }

    public static DiscountType converDiscountType(Integer type) {

        return switch (type) {
            case 0 -> DiscountType.ASSIGNED_TO_ORDER_TOTAL;
            case 1 -> DiscountType.ASSIGNED_TO_PRODUCTS;
            default -> throw new IllegalStateException("Giá trị bất ngờ: " + type);
        };

    }
}
