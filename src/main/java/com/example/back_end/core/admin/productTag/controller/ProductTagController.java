package com.example.back_end.core.admin.productTag.controller;

import com.example.back_end.core.admin.productTag.payload.request.ProductTagRequest;
import com.example.back_end.core.admin.productTag.payload.request.ProductTagSearchRequest;
import com.example.back_end.core.admin.productTag.payload.request.ProductTagUpdateRequest;
import com.example.back_end.core.admin.productTag.payload.response.ProductTagsResponse;
import com.example.back_end.core.common.PageResponse1;
import com.example.back_end.core.common.ResponseData;
import com.example.back_end.service.productTag.ProductTagService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/product-tags")
public class ProductTagController {

    private final ProductTagService productTagService;

    @Operation(method = "GET", summary = "Nhận tất cả thẻ sản phẩm",
            description = "Gửi yêu cầu qua API này để lấy tất cả các thẻ sản phẩm")
    @GetMapping
    public ResponseData<PageResponse1<List<ProductTagsResponse>>> getAll(@ParameterObject ProductTagSearchRequest searchRequest) {

        PageResponse1<List<ProductTagsResponse>> response = productTagService.getAll(searchRequest);

        return ResponseData.<PageResponse1<List<ProductTagsResponse>>>builder()
                .status(HttpStatus.OK.value())
                .message("Nhận thẻ sản phẩm thành công")
                .data(response)
                .build();
    }

    @Operation(method = "POST", summary = "Add new product tag",
            description = "Gửi yêu cầu qua API này để tạo thẻ sản phẩm mới")
    @PostMapping
    public ResponseData<Void> create(@Valid @RequestBody ProductTagRequest request) {

        productTagService.createProductTag(request);

        return ResponseData.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Thêm thẻ sản phẩm thành công")
                .build();
    }

    @PutMapping("/{id}")
    public ResponseData<Void> updateProductTag(@PathVariable Long id, @RequestBody @Valid ProductTagUpdateRequest request) {

        productTagService.updateProductTag(id, request);

        return ResponseData.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Cập nhật thẻ sản phẩm thành công")
                .build();
    }

    @Operation(method = "DELETE", summary = "Xóa thẻ sản phẩm",
            description = "Gửi yêu cầu qua API này để xóa thẻ sản phẩm")
    @DeleteMapping
    public ResponseData<Void> delete(@RequestBody List<Long> ids) {

        productTagService.delete(ids);

        return ResponseData.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Xóa thẻ sản phẩm thành công")
                .build();
    }

}