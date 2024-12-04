package com.example.back_end.core.admin.product.controller;

import com.example.back_end.core.admin.product.payload.request.SpecificationAttributeRequest;
import com.example.back_end.core.admin.product.payload.request.SpecificationAttributeUpdateRequest;
import com.example.back_end.core.admin.product.payload.response.SpecificationAttributeResponse;
import com.example.back_end.core.admin.product.payload.response.SpecificationAttributeUpdateResponse;
import com.example.back_end.service.product.SpecificationAttributeService;
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

    @Operation(method = "POST", summary = "Thêm thuộc tính đặc tả mới",
            description = "Gửi yêu cầu qua API này để tạo thuộc tính đặc tả mới")
    @PostMapping
    public ResponseData<SpecificationAttributeResponse> createSpecificationAttribute(
            @Valid @RequestBody SpecificationAttributeRequest dto
    ) {

        SpecificationAttributeResponse response = specificationAttributesService.createSpecificationAttribute(dto);

        return ResponseData.<SpecificationAttributeResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Thuộc tính đặc tả được tạo thành công")
                .data(response)
                .build();
    }

    @Operation(method = "GET", summary = "Nhận thuộc tính tất cả thông số kỹ thuật",
            description = "Gửi yêu cầu qua API này để lấy tất cả thuộc tính thông số kỹ thuật")
    @GetMapping
    public ResponseData<PageResponse<List<SpecificationAttributeResponse>>> getAll(
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6") int pageSize) {

        PageResponse<List<SpecificationAttributeResponse>> response = specificationAttributesService
                .getAllSpecificationAttribute(name, pageNo, pageSize);

        return ResponseData.<PageResponse<List<SpecificationAttributeResponse>>>builder()
                .status(HttpStatus.OK.value())
                .message("Nhận thành công thuộc tính đặc tả")
                .data(response)
                .build();
    }

    @GetMapping("/{id}")
    public ResponseData<SpecificationAttributeResponse> getSpecificationAttributeById(@PathVariable Long id) {

        SpecificationAttributeResponse attribute = specificationAttributesService.getSpecificationAttributeById(id);

        return ResponseData.<SpecificationAttributeResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Nhận thuộc tính đặc tả bằng id thành công")
                .data(attribute)
                .build();
    }

    @Operation(method = "PUT", summary = "Cập nhật thuộc tính đặc tả",
            description = "Gửi yêu cầu qua API này để cập nhật thuộc tính đặc tả hiện có")
    @PutMapping("/{id}")
    public ResponseData<SpecificationAttributeUpdateResponse> updateSpecificationAttribute(
            @PathVariable Long id,
            @Valid @RequestBody SpecificationAttributeUpdateRequest request) {

        SpecificationAttributeUpdateResponse response = specificationAttributesService
                .editSpecificationAttribute(id, request);

        return ResponseData.<SpecificationAttributeUpdateResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Thuộc tính thông số kỹ thuật được cập nhật thành công")
                .data(response)
                .build();
    }

    @Operation(method = "DELETE", summary = "Xóa các thuộc tính thông số kỹ thuật",
            description = "Gửi yêu cầu qua API này để xóa các thuộc tính đặc tả")
    @DeleteMapping
    public ResponseData<Void> deleteProductAttributes(@RequestBody List<Long> ids) {

        specificationAttributesService.deleteSpecificationAttribute(ids);

        return ResponseData.<Void>builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message("Xóa thành công thuộc tính đặc tả")
                .build();
    }

    @Operation(method = "GET", summary = "Nhận tất cả thuộc tính đặc tả không có nhóm hoặc không hợp lệ",
            description = "Gửi yêu cầu qua API này để lấy tất cả thuộc tính đặc tả không có nhóm")
    @GetMapping("/no-group-or-invalid")
    public ResponseData<PageResponse<List<SpecificationAttributeResponse>>> getAllAttributeNoGroup(
            @RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6") int pageSize) {

        PageResponse<List<SpecificationAttributeResponse>> response = specificationAttributesService
                .getAttributesWithNoGroupOrInvalidGroup(pageNo, pageSize);

        return ResponseData.<PageResponse<List<SpecificationAttributeResponse>>>builder()
                .status(HttpStatus.OK.value())
                .message("Nhận thuộc tính đặc tả thành công không có nhóm")
                .data(response)
                .build();
    }

    @GetMapping("/list-name")
    public ResponseData<List<SpecificationAttributeResponse>> getAllSpecName() {

        List<SpecificationAttributeResponse> response = specificationAttributesService.getAllSpecificationAttributeName();

        return ResponseData.<List<SpecificationAttributeResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Nhận thành công tất cả tên thuộc tính đặc tả")
                .data(response)
                .build();
    }

}
