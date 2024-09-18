package com.example.back_end.infrastructure.utils;

import com.example.back_end.infrastructure.constant.DiscountLimitationType;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class DiscountLimitationTypeConverter extends GenericEnumConverter<DiscountLimitationType, Integer> {

    public DiscountLimitationTypeConverter() {
        super(DiscountLimitationType.class);
    }

}
