package com.example.back_end.core.admin.discount.payload.request;

import com.example.back_end.infrastructure.constant.DiscountType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class VoucherRequest {

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

    BigDecimal minOderAmount;

    List<Long> selectedCustomerIds;

    Boolean isPublished;

    Integer perCustomerLimit;

}