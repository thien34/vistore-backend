package com.example.back_end.core.admin.statistical.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GrowthRate {
    private BigDecimal revenueGrowthRate;
    private BigDecimal orderGrowthRate;
    private BigDecimal productGrowthRate;
}
