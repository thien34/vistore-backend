package com.example.back_end.core.admin.product.payload.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class LocalizedPropertyResponseDto {

    private String localeKey;

    private String localeValue;

}
