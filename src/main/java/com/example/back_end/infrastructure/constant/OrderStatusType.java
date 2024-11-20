package com.example.back_end.infrastructure.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrderStatusType {
    CREATED(0),            // Mới tạo
    PENDING(1),            // Chờ xử lý
    CONFIRMED(2),          // Đã xác nhận
    SHIPPING_PENDING(3),   // Chờ vận chuyển
    SHIPPING_CONFIRMED(4), // Đã xác nhận vận chuyển
    DELIVERING(5),         // Đang giao hàng
    DELIVERED(6),          // Đã giao hàng
    PAID(7),               // Đã thanh toán
    COMPLETED(8);        // Thành công
    public final int value;
}
