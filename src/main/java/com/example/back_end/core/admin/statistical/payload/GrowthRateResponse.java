package com.example.back_end.core.admin.statistical.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GrowthRateResponse {
    private GrowthRate todayGrowthRate;
    private GrowthRate weekGrowthRate;
    private GrowthRate monthGrowthRate;
    private GrowthRate yearGrowthRate;
}
