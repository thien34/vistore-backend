package com.example.back_end.core.admin.product.controller;

import com.example.back_end.core.admin.product.payload.request.ProductSpecificationAttributeMappingRequest;
import com.example.back_end.core.admin.product.payload.request.ProductSpecificationAttributeMappingUpdateRequest;
import com.example.back_end.core.admin.product.payload.response.ProductSpecificationAttributeMappingByIdResponse;
import com.example.back_end.core.admin.product.payload.response.ProductSpecificationAttributeMappingByProductResponse;
import com.example.back_end.core.admin.product.payload.response.ProductSpecificationAttributeMappingResponse;
import com.example.back_end.core.admin.product.payload.response.ProductSpecificationAttributeMappingUpdateResponse;
import com.example.back_end.service.product.ProductSpecificationAttributeMappingService;
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
@RequestMapping("/admin/product-specification-attribute-mappings")
public class ProductSpecificationAttributeMappingController {

    private final ProductSpecificationAttributeMappingService productSpecificationAttributeMappingService;

    @Operation(method = "POST", summary = "Thêm ánh xạ thuộc tính đặc tả sản phẩm mới",
            description = "Gửi yêu cầu qua API này để tạo ánh xạ thuộc tính đặc tả sản phẩm mới")
    @PostMapping
    public ResponseData<ProductSpecificationAttributeMappingResponse> createProductSpecificationAttributeMapping(
            @Valid @RequestBody ProductSpecificationAttributeMappingRequest dto) {

        productSpecificationAttributeMappingService.createProductSpecificationAttributeMapping(dto);

        return ResponseData.<ProductSpecificationAttributeMappingResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Ánh xạ thuộc tính đặc tả sản phẩm được tạo thành công")
                .build();
    }

    @Operation(method = "GET", summary = "Nhận tất cả ánh xạ thuộc tính đặc tả sản phẩm",
            description = "Gửi yêu cầu qua API này để lấy tất cả ánh xạ thuộc tính đặc tả sản phẩm")
    @GetMapping
    public ResponseData<PageResponse<List<ProductSpecificationAttributeMappingResponse>>> getAll(
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6") int pageSize) {

        PageResponse<List<ProductSpecificationAttributeMappingResponse>> response =
                productSpecificationAttributeMappingService
                        .getAllProductSpecificationAttributeMapping(name, pageNo, pageSize);

        return ResponseData.<PageResponse<List<ProductSpecificationAttributeMappingResponse>>>builder()
                .status(HttpStatus.OK.value())
                .message("Ánh xạ thuộc tính đặc tả sản phẩm thành công")
                .data(response)
                .build();
    }

    @Operation(method = "GET", summary = "Nhận ánh xạ thuộc tính đặc tả sản phẩm theo ID",
            description = "Gửi yêu cầu qua API này để lấy ánh xạ thuộc tính đặc tả sản phẩm theo ID")
    @GetMapping("/{id}")
    public ResponseData<ProductSpecificationAttributeMappingByIdResponse> getById(@PathVariable Long id) {

        ProductSpecificationAttributeMappingByIdResponse response = productSpecificationAttributeMappingService
                .getProductSpecificationAttributeMappingById(id);

        return ResponseData.<ProductSpecificationAttributeMappingByIdResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Nhận ánh xạ thuộc tính đặc tả sản phẩm theo ID thành công")
                .data(response)
                .build();
    }

    @Operation(method = "DELETE", summary = "Xóa ánh xạ thuộc tính đặc tả sản phẩm",
            description = "Gửi yêu cầu qua API này để xóa ánh xạ thuộc tính đặc tả sản phẩm")
    @DeleteMapping
    public ResponseData<Void> deleteProductSpecificationAttributeMappings(@RequestBody List<Long> ids) {

        productSpecificationAttributeMappingService.deleteProductSpecificationAttribute(ids);

        return ResponseData.<Void>builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message("Xóa ánh xạ thuộc tính đặc tả sản phẩm thành công")
                .build();
    }

    @Operation(method = "GET", summary = "Nhận ánh xạ thuộc tính đặc tả sản phẩm theo ID sản phẩm",
            description = "Gửi yêu cầu qua API này để lấy ánh xạ thuộc tính đặc tả sản phẩm theo ID sản phẩm")
    @GetMapping("/by-product")
    public ResponseData<PageResponse<List<ProductSpecificationAttributeMappingByProductResponse>>> getByProductId(
            @RequestParam Long productId,
            @RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6") int pageSize) {

        PageResponse<List<ProductSpecificationAttributeMappingByProductResponse>> response =
                productSpecificationAttributeMappingService
                        .getProcSpecMappingsByProductId(productId, pageNo, pageSize);

        return ResponseData.<PageResponse<List<ProductSpecificationAttributeMappingByProductResponse>>>builder()
                .status(HttpStatus.OK.value())
                .message("Nhận ánh xạ thuộc tính đặc tả sản phẩm theo thành công của ID sản phẩm")
                .data(response)
                .build();
    }

    @Operation(method = "PUT", summary = "Cập nhật ánh xạ thuộc tính đặc tả sản phẩm",
            description = "Gửi yêu cầu qua API này để cập nhật ánh xạ thuộc tính đặc tả sản phẩm")
    @PutMapping("/{id}")
    public ResponseData<ProductSpecificationAttributeMappingUpdateResponse> updateProductSpecificationAttributeMapping(
            @PathVariable Long id,
            @Valid @RequestBody ProductSpecificationAttributeMappingUpdateRequest dto) {

        ProductSpecificationAttributeMappingUpdateResponse response =
                productSpecificationAttributeMappingService.updateProductSpecificationAttributeMapping(id, dto);

        return ResponseData.<ProductSpecificationAttributeMappingUpdateResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Ánh xạ thuộc tính đặc tả sản phẩm được cập nhật thành công")
                .data(response)
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseData<Void> deleteProductSpecificationAttributeMapping(@PathVariable Long id) {

        productSpecificationAttributeMappingService.deleteProductSpecificationAttributeMappingById(id);

        return ResponseData.<Void>builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message("Xóa thành công ánh xạ thuộc tính đặc tả sản phẩm")
                .build();
    }

}