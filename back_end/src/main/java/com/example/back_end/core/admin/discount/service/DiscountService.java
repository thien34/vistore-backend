package com.example.back_end.core.admin.discount.service;

import com.example.back_end.core.admin.discount.payload.request.DiscountRequest;
import com.example.back_end.core.admin.discount.payload.response.DiscountFullResponse;
import com.example.back_end.core.admin.discount.payload.response.DiscountResponse;
import com.example.back_end.core.common.PageResponse;
import com.example.back_end.infrastructure.constant.DiscountType;

import java.time.Instant;
import java.util.List;
public interface DiscountService {

    PageResponse<List<DiscountResponse>> getAllDiscounts(
            String name,
            String couponCode,
            DiscountType discountTypeId,
            Instant startDate,
            Instant endDate,
            Boolean isActive,
            int pageNo,
            int pageSize
    );

    DiscountResponse createDiscount(DiscountRequest discountDTO);

    DiscountResponse updateDiscount(Long id, DiscountRequest discountDetails);

    DiscountFullResponse getDiscountById(Long id);

    void deleteDiscount(Long id);

}
