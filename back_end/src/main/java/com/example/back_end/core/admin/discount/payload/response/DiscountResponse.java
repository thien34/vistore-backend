package com.example.back_end.core.admin.discount.payload.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DiscountResponse {

    Long id;

    String name;

    String discountTypeName;

    BigDecimal discountAmount;

    BigDecimal discountPercentage;

    Instant startDateUtc;

    Instant endDateUtc;

    Integer limitationTimes;

    Boolean isActive;

}
