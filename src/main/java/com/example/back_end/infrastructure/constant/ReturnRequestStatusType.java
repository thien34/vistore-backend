package com.example.back_end.infrastructure.constant;

import com.example.back_end.infrastructure.utils.IdentifiableEnum;

public enum ReturnRequestStatusType implements IdentifiableEnum<Integer> {
    RETURN_REQUESTED(0),
    RETURN_AUTHORIZED(1),
    PENDING_SHIPMENT(2),
    SHIPPED_BY_CUSTOMER(3),
    RECEIVED_BY_STORE(4),
    PROCESSING(5),
    REFUND_APPROVED(6),
    REFUND_REJECTED(7), //END PROCESS RETURN
    REFUND_ISSUED(8),
    EXCHANGE_APPROVED(9),
    EXCHANGE_REJECTED(10),  //END PROCESS EXCHANGE
    EXCHANGE_PROCESSING(11),
    EXCHANGE_SHIPPED(12),
    EXCHANGE_ISSUED(11),
    RETURN_COMPLETED(12),  //END PROCESS RETURN
    EXCHANGE_COMPLETED(13),  //END PROCESS EXCHANGE
    CLOSED(14);
    private final int id;

    ReturnRequestStatusType(int id) {
        this.id = id;
    }

    public static ReturnRequestStatusType getById(int id) {
        for (ReturnRequestStatusType type : ReturnRequestStatusType.values()) {
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
