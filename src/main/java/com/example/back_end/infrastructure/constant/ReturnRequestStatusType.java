package com.example.back_end.infrastructure.constant;

import com.example.back_end.infrastructure.utils.IdentifiableEnum;

public enum ReturnRequestStatusType implements IdentifiableEnum<Integer> {
    CREATED(0),
    PENDING(1),
    APPROVED(2),
    REJECTED(3),
    EXCHANGED(4),
    REFUNDED(5),
    CLOSED(6);
    private final int id;

    ReturnRequestStatusType(int id) {
        this.id = id;
    }

    public static ReturnRequestStatusType getById(int id) {
        for (ReturnRequestStatusType type :ReturnRequestStatusType.values()) {
            if (type.id == id) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid return request status type id: " + id);
    }

    @Override
    public Integer getId() {
        return this.id;
    }
}
