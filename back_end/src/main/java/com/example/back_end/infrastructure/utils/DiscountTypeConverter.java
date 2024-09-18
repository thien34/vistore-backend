package com.example.back_end.infrastructure.utils;

import com.example.back_end.infrastructure.constant.DiscountType;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class DiscountTypeConverter extends GenericEnumConverter<DiscountType, Integer> {

    public DiscountTypeConverter() {
        super(DiscountType.class);
    }

}
