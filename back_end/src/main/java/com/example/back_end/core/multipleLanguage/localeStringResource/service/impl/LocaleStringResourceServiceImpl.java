package com.example.back_end.core.multipleLanguage.localeStringResource.service.impl;

import com.example.back_end.core.multipleLanguage.localeStringResource.mapper.LocaleStringResourceMapper;
import com.example.back_end.core.multipleLanguage.localeStringResource.payload.response.LocaleStringResourceDtoResponse;
import com.example.back_end.core.multipleLanguage.localeStringResource.service.LocaleStringResourceService;
import com.example.back_end.entity.LocaleStringResource;
import com.example.back_end.infrastructure.exception.ResourceNotFoundException;
import com.example.back_end.repository.LocaleStringResourceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class LocaleStringResourceServiceImpl implements LocaleStringResourceService {

    private final LocaleStringResourceRepository localeStringResourceRepository;
    private final LocaleStringResourceMapper localeStringResourceMapper;

    @Override
    public List<LocaleStringResourceDtoResponse> findByLanguageName(String languageName) {
        List<LocaleStringResource> localeStringResources = localeStringResourceRepository.findByLanguageName(languageName);

        if (localeStringResources.isEmpty()) {
            log.warn("No resources found for language: {}", languageName);
            throw new ResourceNotFoundException("No resources found for language: " + languageName);
        }

        return localeStringResources.stream()
                .map(localeStringResourceMapper::toDto)
                .toList();
    }
}
