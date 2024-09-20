package com.example.back_end.core.admin.discount.payload.request;

import com.example.back_end.infrastructure.constant.DiscountType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DiscountRequest {

    Boolean isActive;

    String name;

    DiscountType discountTypeId;

    Boolean appliedToSubCategories;

    Boolean usePercentage;

    BigDecimal discountPercentage;

    BigDecimal maxDiscountAmount;

    Boolean requiresCouponCode;

    String couponCode;

    Integer discountLimitationId;

    Instant startDateUtc;

    Instant endDateUtc;

    String comment;

    BigDecimal discountAmount;

    Boolean isCumulative;

    Integer limitationTimes;

    Integer maxDiscountedQuantity;

    BigDecimal minOderAmount;

}

