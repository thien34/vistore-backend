package com.example.back_end.infrastructure.constant;

import com.example.back_end.infrastructure.utils.IdentifiableEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DiscountType implements IdentifiableEnum<Integer> {
    ASSIGNED_TO_ORDER_TOTAL(0),
    ASSIGNED_TO_PRODUCTS(1),
    ASSIGNED_TO_CATEGORIES(2),
    ASSIGNED_TO_MANUFACTURERS(3),
    ASSIGNED_TO_ORDER_SUBTOTAL(4);

    private final int id;

    public static DiscountType getById(int id) {
        for (DiscountType type : DiscountType.values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid discount type id: " + id);
    }

    @Override
    public Integer getId() {
        return this.id;
    }
}
