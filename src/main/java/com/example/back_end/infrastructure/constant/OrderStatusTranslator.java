package com.example.back_end.infrastructure.constant;

import java.util.Map;

public class OrderStatusTranslator {

    public static final Map<OrderStatusType, String> STATUS_TRANSLATIONS = Map.of(
            OrderStatusType.CREATED, "Mới tạo",
            OrderStatusType.PENDING, "Chờ xử lý",
            OrderStatusType.CONFIRMED, "Đã xác nhận",
            OrderStatusType.SHIPPING_PENDING, "Chờ vận chuyển",
            OrderStatusType.SHIPPING_CONFIRMED, "Đã xác nhận vận chuyển",
            OrderStatusType.DELIVERING, "Đang giao hàng",
            OrderStatusType.DELIVERED, "Đã giao hàng",
            OrderStatusType.PAID, "Đã thanh toán",
            OrderStatusType.COMPLETED, "Thành công",
            OrderStatusType.CANCELLED, "Hủy"
    );

    public static String translate(OrderStatusType status) {
        return STATUS_TRANSLATIONS.getOrDefault(status, "Không xác định");
    }
}
