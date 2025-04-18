package com.example.back_end.core.admin.discount.payload.response;

import com.example.back_end.core.admin.customer.payload.response.CustomerResponse;
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
public class VoucherFullResponse {

    Long id;

    Boolean isActive;

    String name;

    Integer discountTypeId;

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

    String status;

    Integer usageCount;

    List<CustomerResponse> appliedCustomers;
}
