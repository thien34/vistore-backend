package com.example.back_end.core.admin.discount.service;

import com.example.back_end.core.admin.discount.payload.request.DiscountFilterRequest;
import com.example.back_end.core.admin.discount.payload.request.DiscountRequest;
import com.example.back_end.core.admin.discount.payload.response.DiscountFullResponse;
import com.example.back_end.core.admin.discount.payload.response.DiscountNameResponse;
import com.example.back_end.core.admin.discount.payload.response.DiscountResponse;
import com.example.back_end.core.common.PageResponse;

import java.util.List;

public interface DiscountService {

    List<DiscountNameResponse> getAllDiscounts(Integer type);

    PageResponse<List<DiscountResponse>> getAllDiscounts(DiscountFilterRequest filterRequest);

    void createDiscount(DiscountRequest discountDTO);

    void updateDiscount(Long id, DiscountRequest discountDetails);

    DiscountFullResponse getDiscountById(Long id);

    void deleteDiscount(Long id);

}
