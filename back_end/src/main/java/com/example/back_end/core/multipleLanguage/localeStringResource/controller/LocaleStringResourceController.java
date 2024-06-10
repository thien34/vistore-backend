package com.example.back_end.core.multipleLanguage.localeStringResource.controller;

import com.example.back_end.core.common.ResponseData;
import com.example.back_end.core.common.ResponseError;
import com.example.back_end.core.multipleLanguage.localeStringResource.payload.response.LocaleStringResourceDtoResponse;
import com.example.back_end.core.multipleLanguage.localeStringResource.service.LocaleStringResourceService;
import com.example.back_end.infrastructure.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/locale_string_resources")
@RequiredArgsConstructor
@Slf4j
public class LocaleStringResourceController {

    private static final String ERROR_MESSAGE = "errorMessage={}";
    private final LocaleStringResourceService localeStringResourceService;

    @GetMapping
    public ResponseData<List<LocaleStringResourceDtoResponse>> findByLanguageName(@RequestParam(value = "languageName", defaultValue = "vi") String languageName) {
        log.info("Request languageName={}", languageName);

        try {
            List<LocaleStringResourceDtoResponse> resources = localeStringResourceService.findByLanguageName(languageName);
            return new ResponseData<>(HttpStatus.OK.value(), "Success", resources);
        } catch (ResourceNotFoundException e) {
            log.error(ERROR_MESSAGE, e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }
}
