package com.example.back_end.core.admin.discount.mapper;

import com.example.back_end.core.admin.discount.payload.response.DiscountNameResponse;
import com.example.back_end.entity.Discount;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DiscountMapper {

    DiscountNameResponse toDiscountNameResponse(Discount discount);
}
