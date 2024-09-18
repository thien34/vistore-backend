package com.example.back_end.infrastructure.constant;

import com.example.back_end.infrastructure.utils.IdentifiableEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DiscountLimitationType implements IdentifiableEnum<Integer> {

    NONE(0),
    N_TIMES_ONLY(1),
    N_TIMES_PER_CUSTOMER(2);

    private final int id;

    @Override
    public Integer getId() {
        return this.id;
    }

    public static DiscountLimitationType getById(int id) {
        for (DiscountLimitationType type : values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid discount limitation type id: " + id);
    }
}
