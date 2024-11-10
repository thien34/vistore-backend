package com.example.back_end.infrastructure.constant;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum PaymentStatusType {
    PENDING(0),            // Chờ thanh toán
    PAID(1),               // Đã thanh toán
    CANCELLED(2),          // Đã hủy
    CASH_ON_DELIVERY(3);   // Thanh toán khi nhận hàng

    public final int value;
}

