package com.example.back_end.infrastructure.constant;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum OrderStatusType {
    PENDING(0),            // Chờ xử lý
    CONFIRMED(1),          // Đã xác nhận
    SHIPPING_PENDING(2),   // Chờ vận chuyển
    SHIPPING_CONFIRMED(3), // Đã xác nhận vận chuyển
    DELIVERING(4),         // Đang giao hàng
    DELIVERED(5),          // Đã giao hàng
    PAID(6),               // Đã thanh toán
    COMPLETED(7),          // Thành công
    CANCELLED(8);          // Đã hủy

    public final int code;
}
