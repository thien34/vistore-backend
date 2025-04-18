package com.example.back_end.service.discount;

import com.example.back_end.core.admin.discount.payload.request.DiscountFilterRequest;
import com.example.back_end.core.admin.discount.payload.request.DiscountRequest;
import com.example.back_end.core.admin.discount.payload.response.DiscountFullResponse;
import com.example.back_end.core.admin.discount.payload.response.DiscountNameResponse;
import com.example.back_end.core.admin.discount.payload.response.DiscountResponse;

import java.util.List;

public interface DiscountService {

    List<DiscountNameResponse> getAllDiscounts(Integer type);

    List<DiscountResponse> getAllDiscounts(DiscountFilterRequest filterRequest);

    void createDiscount(DiscountRequest discountDTO);

    void updateDiscount(Long id, DiscountRequest discountDetails);

    DiscountFullResponse getDiscountById(Long id);

    void deleteDiscount(Long id);

    List<DiscountResponse> getDiscountsByType(Integer discountType);

    List<DiscountResponse> getDiscountsByProductId(Long productId);

    void updateEndDateToNow(Long id);

    void cancelDiscount(Long id);

}
