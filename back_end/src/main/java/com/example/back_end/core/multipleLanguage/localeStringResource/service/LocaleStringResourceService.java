package com.example.back_end.core.multipleLanguage.localeStringResource.service;

import com.example.back_end.core.multipleLanguage.localeStringResource.payload.response.LocaleStringResourceDtoResponse;

import java.util.List;

public interface LocaleStringResourceService {

    List<LocaleStringResourceDtoResponse> findByLanguageName(String languageName);

}
