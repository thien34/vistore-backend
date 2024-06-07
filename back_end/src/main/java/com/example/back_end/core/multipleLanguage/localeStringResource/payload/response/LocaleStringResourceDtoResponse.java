package com.example.back_end.core.multipleLanguage.localeStringResource.payload.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LocaleStringResourceDtoResponse {

    private Long id;
    private String resourceName;
    private String resourceValue;

}
