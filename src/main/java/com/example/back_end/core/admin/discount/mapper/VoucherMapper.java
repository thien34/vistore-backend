package com.example.back_end.core.admin.discount.mapper;

import com.example.back_end.core.admin.discount.payload.request.VoucherRequest;
import com.example.back_end.core.admin.discount.payload.response.VoucherFullResponse;
import com.example.back_end.core.admin.discount.payload.response.VoucherResponse;
import com.example.back_end.entity.Discount;
import com.example.back_end.infrastructure.constant.DiscountLimitationType;
import com.example.back_end.infrastructure.constant.DiscountType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VoucherMapper {
    @Mapping(target = "discountTypeName", source = "discountTypeId", qualifiedByName = "mapDiscountTypeName")
    VoucherResponse toResponse(Discount discount);

    @Mapping(source = "discountTypeId", target = "discountTypeId")
    @Mapping(source = "discountLimitationId", target = "discountLimitationId")
    VoucherFullResponse toFullResponse(Discount discount);

    default Integer map(DiscountType value) {
        return value != null ? value.getId() : null;
    }

    default Integer map(DiscountLimitationType value) {
        return value != null ? value.getId() : null;
    }
    List<VoucherResponse> toResponseList(List<Discount> discounts);
    @Mapping(target = "discountLimitationId", source = "discountLimitationId", qualifiedByName = "mapDiscountLimitationType")
    Discount toEntity(VoucherRequest request);

    @Named("mapDiscountTypeName")
    default String mapDiscountTypeName(DiscountType discountType) {
        return discountType != null ? discountType.name() : null;
    }
    @Named("mapDiscountLimitationType")
    default DiscountLimitationType mapDiscountLimitationType(Integer discountLimitationId) {
        if (discountLimitationId == null) {
            return null;
        }
        return DiscountLimitationType.getById(discountLimitationId);
    }
}

