package com.example.back_end.core.admin.discount.payload.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class CouponRequest {
    private List<String> couponCodes;
}
