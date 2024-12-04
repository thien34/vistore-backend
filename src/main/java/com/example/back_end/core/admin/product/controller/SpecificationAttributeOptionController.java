package com.example.back_end.core.admin.product.controller;

import com.example.back_end.core.admin.product.payload.request.SpecificationAttributeOptionRequest;
import com.example.back_end.core.admin.product.payload.response.SpecificationAttributeOptionNameResponse;
import com.example.back_end.core.admin.product.payload.response.SpecificationAttributeOptionResponse;
import com.example.back_end.service.product.SpecificationAttributeOptionService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/specification-attribute-options")
public class SpecificationAttributeOptionController {

    private final SpecificationAttributeOptionService specificationAttributeOptionService;

    @Operation(method = "POST", summary = "Thêm tùy chọn thuộc tính đặc tả mới",
            description = "Gửi yêu cầu qua API này để tạo tùy chọn thuộc tính đặc tả mới")
    @PostMapping
    public ResponseData<SpecificationAttributeOptionResponse> createSpecificationAttributeOption(
            @Valid @RequestBody SpecificationAttributeOptionRequest dto
    ) {

        SpecificationAttributeOptionResponse response =
                specificationAttributeOptionService.createSpecificationAttributeOption(dto);

        return ResponseData.<SpecificationAttributeOptionResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Tạo tùy chọn thuộc tính đặc tả thành công")
                .data(response)
                .build();

    }

    @Operation(method = "GET", summary = "Lấy tất cả tùy chọn theo ID thuộc tính đặc tả",
            description = "Gửi yêu cầu qua API này để lấy tất cả tùy chọn theo ID thuộc tính đặc tả")
    @GetMapping("/by-spec/{specificationAttributeId}")
    public ResponseData<List<SpecificationAttributeOptionNameResponse>> getAllOptionsBySpecificationAttributeId(
            @PathVariable Long specificationAttributeId) {

        List<SpecificationAttributeOptionNameResponse> response =
                specificationAttributeOptionService.getAllOptionsBySpecificationAttributeId(specificationAttributeId);

        return ResponseData.<List<SpecificationAttributeOptionNameResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Lấy các tùy chọn thuộc tính đặc tả theo ID thuộc tính đặc tả thành công")
                .data(response)
                .build();
    }

    @Operation(method = "GET", summary = "Lấy tất cả tùy chọn thuộc tính đặc tả",
            description = "Gửi yêu cầu qua API này để lấy tất cả tùy chọn thuộc tính đặc tả")
    @GetMapping
    public ResponseData<PageResponse<List<SpecificationAttributeOptionResponse>>> getAll(
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6") int pageSize) {

        PageResponse<List<SpecificationAttributeOptionResponse>> response = specificationAttributeOptionService
                .getAllSpecificationAttributeOption(name, pageNo, pageSize);

        return ResponseData.<PageResponse<List<SpecificationAttributeOptionResponse>>>builder()
                .status(HttpStatus.OK.value())
                .message("Lấy các tùy chọn thuộc tính đặc tả thành công")
                .data(response)
                .build();
    }

    @Operation(method = "DELETE", summary = "Xóa các tùy chọn thuộc tính đặc tả",
            description = "Gửi yêu cầu qua API này để xóa các tùy chọn thuộc tính đặc tả")
    @DeleteMapping
    public ResponseData<Void> deleteSpecificationAttributeOptions(@RequestBody List<Long> ids) {

        specificationAttributeOptionService.deleteSpecificationAttributeOption(ids);

        return ResponseData.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Xóa các tùy chọn thuộc tính đặc tả thành công")
                .build();
    }

}
