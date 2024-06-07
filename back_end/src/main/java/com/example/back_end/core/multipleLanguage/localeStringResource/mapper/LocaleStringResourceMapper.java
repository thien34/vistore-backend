package com.example.back_end.core.multipleLanguage.localeStringResource.mapper;

import com.example.back_end.core.multipleLanguage.localeStringResource.payload.response.LocaleStringResourceDtoResponse;
import com.example.back_end.entity.LocaleStringResource;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface LocaleStringResourceMapper {

    LocaleStringResourceDtoResponse toDto(LocaleStringResource localeStringResource);

}
