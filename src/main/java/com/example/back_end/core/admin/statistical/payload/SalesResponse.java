package com.example.back_end.core.admin.statistical.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SalesResponse {
    private int totalInvoices; // Số đơn tổng cộng
    private BigDecimal totalRevenue; // Tổng doanh thu
    private int successfulOrders; // Số đơn thành công
    private int cancelledOrders; // Số đơn bị hủy
    private int totalProducts; // Tổng số sản phẩm
}

