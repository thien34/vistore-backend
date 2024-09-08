package com.example.back_end.core.admin.discount.service;

import com.example.back_end.core.admin.discount.payload.response.DiscountNameResponse;

import java.util.List;

public interface DiscountService {

    List<DiscountNameResponse> getAllDiscounts(Integer type);
}
