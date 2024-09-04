package com.example.back_end.core.admin.discount.service;

import com.example.back_end.core.admin.discount.payload.request.DiscountRequest;
import com.example.back_end.core.admin.discount.payload.response.DiscountResponse;
import com.example.back_end.core.common.PageResponse;

import java.util.List;
public interface DiscountService {

    PageResponse<List<DiscountResponse>> getAllDiscounts(int pageNo, int pageSize);

    DiscountResponse createDiscount(DiscountRequest discountDTO);

    DiscountResponse updateDiscount(Long id, DiscountRequest discountDetails);

    void deleteDiscount(Long id);

}
