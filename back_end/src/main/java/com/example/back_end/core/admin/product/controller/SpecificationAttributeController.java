package com.example.back_end.core.admin.product.controller;

import com.example.back_end.core.admin.product.payload.request.SpecificationAttributeRequest;
import com.example.back_end.core.admin.product.payload.request.SpecificationAttributeUpdateRequest;
import com.example.back_end.core.admin.product.payload.response.SpecificationAttributeResponse;
import com.example.back_end.core.admin.product.payload.response.SpecificationAttributeUpdateResponse;
import com.example.back_end.core.admin.product.service.SpecificationAttributeService;
import com.example.back_end.core.common.PageResponse;
import com.example.back_end.core.common.ResponseData;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@RequestMapping("/admin/specification-attributes")
public class SpecificationAttributeController {

    private final SpecificationAttributeService specificationAttributesService;

    @Operation(method = "POST", summary = "Add new specification attribute",
            description = "Send a request via this API to create new specification attribute")
    @PostMapping
    public ResponseData<SpecificationAttributeResponse> createSpecificationAttribute(
            @Valid @RequestBody SpecificationAttributeRequest dto
    ) {

        SpecificationAttributeResponse response = specificationAttributesService.createSpecificationAttribute(dto);

        return ResponseData.<SpecificationAttributeResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Specification attribute created successfully")
                .data(response)
                .build();
    }

    @Operation(method = "GET", summary = "Get all specification attribute",
            description = "Send a request via this API to get all specification attribute")
    @GetMapping
    public ResponseData<PageResponse<List<SpecificationAttributeResponse>>> getAll(
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6") int pageSize) {

        PageResponse<List<SpecificationAttributeResponse>> response = specificationAttributesService
                .getAllSpecificationAttribute(name, pageNo, pageSize);

        return ResponseData.<PageResponse<List<SpecificationAttributeResponse>>>builder()
                .status(HttpStatus.OK.value())
                .message("Get specification attribute success")
                .data(response)
                .build();
    }

    @GetMapping("/{id}")
    public ResponseData<SpecificationAttributeResponse> getSpecificationAttributeById(@PathVariable Long id) {

        SpecificationAttributeResponse attribute = specificationAttributesService.getSpecificationAttributeById(id);

        return ResponseData.<SpecificationAttributeResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Get specification attribute by id successfully")
                .data(attribute)
                .build();
    }

    @Operation(method = "PUT", summary = "Update specification attribute",
            description = "Send a request via this API to update an existing specification attribute")
    @PutMapping("/{id}")
    public ResponseData<SpecificationAttributeUpdateResponse> updateSpecificationAttribute(
            @PathVariable Long id,
            @Valid @RequestBody SpecificationAttributeUpdateRequest request) {

        SpecificationAttributeUpdateResponse response = specificationAttributesService
                .editSpecificationAttribute(id, request);

        return ResponseData.<SpecificationAttributeUpdateResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Specification attribute updated successfully")
                .data(response)
                .build();
    }

    @Operation(method = "DELETE", summary = "Delete specification attributes",
            description = "Send a request via this API to delete specification attributes")
    @DeleteMapping
    public ResponseData<Void> deleteProductAttributes(@RequestBody List<Long> ids) {

        specificationAttributesService.deleteSpecificationAttribute(ids);

        return ResponseData.<Void>builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message("Delete specification attributes success")
                .build();
    }

    @Operation(method = "GET", summary = "Get all specification attribute no group or invalid",
            description = "Send a request via this API to get all specification attribute no group")
    @GetMapping("/no-group-or-invalid")
    public ResponseData<PageResponse<List<SpecificationAttributeResponse>>> getAllAttributeNoGroup(
            @RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6") int pageSize) {

        PageResponse<List<SpecificationAttributeResponse>> response = specificationAttributesService
                .getAttributesWithNoGroupOrInvalidGroup(pageNo, pageSize);

        return ResponseData.<PageResponse<List<SpecificationAttributeResponse>>>builder()
                .status(HttpStatus.OK.value())
                .message("Get specification attribute success no group")
                .data(response)
                .build();
    }

    @GetMapping("/list-name")
    public ResponseData<List<SpecificationAttributeResponse>> getAllSpecName() {

        List<SpecificationAttributeResponse> response = specificationAttributesService.getAllSpecificationAttributeName();

        return ResponseData.<List<SpecificationAttributeResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Get all specification attribute name successfully")
                .data(response)
                .build();
    }

}
