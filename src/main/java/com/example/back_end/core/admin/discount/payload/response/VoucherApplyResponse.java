package com.example.back_end.core.admin.discount.payload.response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VoucherApplyResponse {
    private String couponCode;
    private Boolean isApplicable;
    private String reason;
    private BigDecimal discountAmount;
}
