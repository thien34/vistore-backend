package com.example.back_end.core.admin.product.controller;

import com.example.back_end.core.admin.product.payload.request.SpecificationAttributeRequest;
import com.example.back_end.core.admin.product.payload.request.SpecificationAttributeUpdateRequest;
import com.example.back_end.core.admin.product.payload.response.SpecificationAttributeResponse;
import com.example.back_end.core.admin.product.payload.response.SpecificationAttributeUpdateResponse;
import com.example.back_end.core.admin.product.service.SpecificationAttributeService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/specification-attributes")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class SpecificationAttributeController {
    SpecificationAttributeService specificationAttributesService;
    @Operation(method = "POST", summary = "Add new specification attribute",
            description = "Send a request via this API to create new specification attribute")
    @PostMapping
    public ResponseData<SpecificationAttributeResponse> createSpecificationAttribute(
            @Valid @RequestBody SpecificationAttributeRequest dto) {
        try {
            SpecificationAttributeResponse response = specificationAttributesService.createSpecificationAttribute(dto);
            return ResponseData.<SpecificationAttributeResponse>builder()
                    .status(HttpStatus.OK.value())
                    .message(SuccessCode.SPECIFICATION_ATTRIBUTE_CREATED.getMessage())
                    .data(response)
                    .build();
        } catch (Exception e) {
            log.error("Error creating specification attribute", e);
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

    @Operation(method = "GET", summary = "Get all specification attribute",
            description = "Send a request via this API to get all specification attribute")
    @GetMapping
    public ResponseData<?> getAll(@RequestParam(value = "name", defaultValue = "") String name,
                                  @RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
                                  @RequestParam(value = "pageSize", defaultValue = "6") int pageSize) {
        try {
            PageResponse<?> response = specificationAttributesService.getAllSpecificationAttribute(name, pageNo, pageSize);
            return new ResponseData<>(HttpStatus.OK.value(), "Get specification attribute success", response);
        } catch (Exception e) {
            log.error("Error getting specification attribute ", e);
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }
    @GetMapping("/{id}")
    public ResponseData<SpecificationAttributeResponse> getSpecificationAttributeById(@PathVariable Long id) {
        SpecificationAttributeResponse attribute = specificationAttributesService.getSpecificationAttributeById(id);
        return ResponseData.<SpecificationAttributeResponse>builder()
                .status(SuccessCode.GET_SPECIFICATION_ATTRIBUTE_BY_ID.getStatusCode().value())
                .message(SuccessCode.GET_SPECIFICATION_ATTRIBUTE_BY_ID.getMessage())
                .data(attribute)
                .build();
    }
    @Operation(method = "PUT", summary = "Update specification attribute",
            description = "Send a request via this API to update an existing specification attribute")
    @PutMapping("/{id}")
    public ResponseData<SpecificationAttributeUpdateResponse> updateSpecificationAttribute(
            @PathVariable Long id,
            @Valid @RequestBody SpecificationAttributeUpdateRequest request) {
        try {
            SpecificationAttributeUpdateResponse response = specificationAttributesService.editSpecificationAttribute(id, request);
            return ResponseData.<SpecificationAttributeUpdateResponse>builder()
                    .status(HttpStatus.OK.value())
                    .message(SuccessCode.SPECIFICATION_ATTRIBUTE_UPDATED.getMessage())
                    .data(response)
                    .build();
        } catch (Exception e) {
            log.error("Error updating specification attribute", e);
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }
    @Operation(method = "DELETE", summary = "Delete specification attributes",
            description = "Send a request via this API to delete specification attributes")
    @DeleteMapping
    public ResponseData<ResponseError> deleteProductAttributes(@RequestBody List<Long> ids) {
        log.info("Request to delete specification attributes with ids: {}", ids);
        try {
            specificationAttributesService.deleteSpecificationAttribute(ids);
            return new ResponseData<>(HttpStatus.OK.value(), "Delete specification attributes success");
        } catch (Exception e) {
            log.error("Error deleting specification attributes", e);
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }
    @Operation(method = "GET", summary = "Get all specification attribute no group or invalid",
            description = "Send a request via this API to get all specification attribute no group")
    @GetMapping("/no-group-or-invalid")
    public ResponseData<?> getAllAttribute(@RequestParam(value = "name", defaultValue = "") String name,
                                  @RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
                                  @RequestParam(value = "pageSize", defaultValue = "6") int pageSize) {
        try {
            PageResponse<?> response = specificationAttributesService.getAttributesWithNoGroupOrInvalidGroup(pageNo, pageSize);
            return new ResponseData<>(HttpStatus.OK.value(), "Get specification attribute success no group", response);
        } catch (Exception e) {
            log.error("Error getting specification attribute no group", e);
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }
}
