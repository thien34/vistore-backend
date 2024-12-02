package com.example.back_end.core.admin.discount.payload.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ValidateCouponsRequest {
    private BigDecimal subTotal;
    private List<String> couponCodes;
    private String email;
}
