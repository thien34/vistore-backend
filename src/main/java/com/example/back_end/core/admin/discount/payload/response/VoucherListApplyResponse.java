package com.example.back_end.core.admin.discount.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VoucherListApplyResponse {

    private String couponCode;

    private BigDecimal discountAmount;

}
