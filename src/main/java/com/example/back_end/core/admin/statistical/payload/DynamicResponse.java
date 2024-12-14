package com.example.back_end.core.admin.statistical.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DynamicResponse {
    private TotalRevenueResponse totalRevenue;
    private List<ProductSaleResponse> bestSellingProducts;
    private List<ProductOutOfStockResponse> outOfStockProducts;
    private List<OrderStatusSummaryResponse> orderStatusChart;
}
