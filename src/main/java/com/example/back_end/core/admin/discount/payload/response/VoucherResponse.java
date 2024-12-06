package com.example.back_end.core.admin.discount.payload.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VoucherResponse {

    Long id;

    String name;

    String discountTypeName;

    BigDecimal discountAmount;

    BigDecimal discountPercentage;

    Instant startDateUtc;

    Instant endDateUtc;

    Integer limitationTimes;

    String status;

    Boolean requiresCouponCode;

    String couponCode;

    BigDecimal maxDiscountAmount;

    BigDecimal minOderAmount;

    Boolean usePercentage;

    Boolean isCumulative;

    List<Long> appliedCustomerIds;

    Boolean isPublished;

    Integer usageCount;
}
