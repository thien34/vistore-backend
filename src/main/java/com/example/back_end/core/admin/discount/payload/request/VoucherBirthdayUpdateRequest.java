package com.example.back_end.core.admin.discount.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VoucherBirthdayUpdateRequest {

    private Boolean usePercentage;

    private BigDecimal discountAmount;

    private BigDecimal discountPercentage;

    private BigDecimal maxDiscountAmount;

    private Boolean isCumulative;

    private Integer limitationTimes;

    private Integer perCustomerLimit;

    private BigDecimal minOrderAmount;

}
