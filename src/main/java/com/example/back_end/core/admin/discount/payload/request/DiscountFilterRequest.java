package com.example.back_end.core.admin.discount.payload.request;

import com.example.back_end.infrastructure.constant.DiscountType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
public class DiscountFilterRequest {

    private String name;

    private String couponCode;

    private DiscountType discountTypeId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Instant startDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Instant endDate;

    private Boolean isActive;

    private Integer pageNo;

    private Integer pageSize;

}
