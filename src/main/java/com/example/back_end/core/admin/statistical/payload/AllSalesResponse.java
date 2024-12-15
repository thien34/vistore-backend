package com.example.back_end.core.admin.statistical.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AllSalesResponse {
    private SalesResponse todaySales;
    private SalesResponse weekSales;
    private SalesResponse monthSales;
    private SalesResponse yearSales;
    private GrowthRateResponse growthRate;
}
