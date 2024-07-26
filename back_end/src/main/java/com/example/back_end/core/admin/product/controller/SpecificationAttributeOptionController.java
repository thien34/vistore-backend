package com.example.back_end.core.admin.product.controller;

import com.example.back_end.core.admin.product.payload.request.SpecificationAttributeOptionRequest;
import com.example.back_end.core.admin.product.payload.response.SpecificationAttributeOptionResponse;
import com.example.back_end.core.admin.product.service.SpecificationAttributeOptionService;
import com.example.back_end.core.common.PageResponse;
import com.example.back_end.core.common.ResponseData;
import com.example.back_end.core.common.ResponseError;
import com.example.back_end.infrastructure.constant.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/specification-attribute-options")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class SpecificationAttributeOptionController {
    SpecificationAttributeOptionService specificationAttributeOptionService;
    @Operation(method = "POST", summary = "Add new specification attribute option",
            description = "Send a request via this API to create new specification attribute option")
    @PostMapping
    public ResponseData<SpecificationAttributeOptionResponse> createSpecificationAttributeOption(
            @Valid @RequestBody SpecificationAttributeOptionRequest dto) {
        try {
            SpecificationAttributeOptionResponse response =
                    specificationAttributeOptionService.createSpecificationAttributeOption(dto);
            return ResponseData.<SpecificationAttributeOptionResponse>builder()
                    .status(HttpStatus.OK.value())
                    .message(SuccessCode.SPECIFICATION_ATTRIBUTE_CREATED.getMessage())
                    .data(response)
                    .build();
        } catch (Exception e) {
            log.error("Error creating specification attribute options", e);
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }
    @Operation(method = "GET", summary = "Get all specification attribute options",
            description = "Send a request via this API to get all specification attribute options")
    @GetMapping
    public ResponseData<?> getAll(@RequestParam(value = "name", defaultValue = "") String name,
                                  @RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
                                  @RequestParam(value = "pageSize", defaultValue = "6") int pageSize) {
        try {
            PageResponse<?> response = specificationAttributeOptionService.getAllSpecificationAttributeOption(name, pageNo, pageSize);
            return new ResponseData<>(HttpStatus.OK.value(), "Get specification attribute options success", response);
        } catch (Exception e) {
            log.error("Error getting specification attribute options", e);
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }
    @Operation(method = "DELETE", summary = "Delete specification attribute options",
            description = "Send a request via this API to delete specification attribute options")
    @DeleteMapping
    public ResponseData<?> deleteSpecificationAttributeOptions(@RequestBody List<Long> ids) {
        log.info("Request to delete specification attribute options with ids: {}", ids);
        try {
            specificationAttributeOptionService.deleteSpecificationAttributeOption(ids);
            return new ResponseData<>(HttpStatus.OK.value(), "Delete specification attribute options success");
        } catch (Exception e) {
            log.error("Error deleting specification attribute options", e);
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }
}
