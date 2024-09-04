package com.example.back_end.core.admin.discount.mapper;

import com.example.back_end.core.admin.discount.payload.request.DiscountRequest;
import com.example.back_end.core.admin.discount.payload.response.DiscountResponse;
import com.example.back_end.entity.Discount;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DiscountMapper {
    DiscountResponse toResponse(Discount discount);
    Discount toEntity(DiscountRequest request);
    void updateEntityFromRequest(DiscountRequest discountRequest, @MappingTarget Discount discount);

}
