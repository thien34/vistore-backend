package com.example.back_end.core.admin.discount.service.impl;

import com.example.back_end.core.admin.discount.mapper.DiscountMapper;
import com.example.back_end.core.admin.discount.payload.response.DiscountNameResponse;
import com.example.back_end.core.admin.discount.service.DiscountService;
import com.example.back_end.entity.Discount;
import com.example.back_end.infrastructure.constant.DiscountType;
import com.example.back_end.infrastructure.utils.ConvertEnumTypeUtils;
import com.example.back_end.repository.DiscountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscountServiceImpl implements DiscountService {
    private final DiscountRepository discountRepository;
    private final DiscountMapper discountMapper;
    @Override
    public List<DiscountNameResponse> getAllDiscounts(Integer type) {

        List<Discount> discounts = discountRepository.findAll();

        DiscountType discountType = ConvertEnumTypeUtils.converDiscountType(type);

        return discounts
                .stream().filter(discount -> discount.getDiscountTypeId() == discountType)
                .map(discountMapper::toDiscountNameResponse)
                .toList();
    }

}
