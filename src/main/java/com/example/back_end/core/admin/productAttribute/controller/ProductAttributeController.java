package com.example.back_end.core.admin.productAttribute.controller;

import com.example.back_end.core.admin.productAttribute.payload.request.ProdAttrSearchRequest;
import com.example.back_end.core.admin.productAttribute.payload.request.ProductAttributeRequest;
import com.example.back_end.core.admin.productAttribute.payload.response.ProductAttributeNameResponse;
import com.example.back_end.core.admin.productAttribute.payload.response.ProductAttributeResponse;
import com.example.back_end.core.common.PageResponse1;
import com.example.back_end.core.common.ResponseData;
import com.example.back_end.service.productAttribute.ProductAttributeService;
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
@RequestMapping("/admin/product-attributes")
public class ProductAttributeController {

    private final ProductAttributeService productAttributeService;

    @GetMapping
    public ResponseData<PageResponse1<List<ProductAttributeResponse>>> getAll(@ParameterObject ProdAttrSearchRequest searchRequest) {

        PageResponse1<List<ProductAttributeResponse>> response = productAttributeService
                .getAllProductAttribute(searchRequest);

        return ResponseData.<PageResponse1<List<ProductAttributeResponse>>>builder()
                .status(HttpStatus.OK.value())
                .message("Nhận thành công tất cả thuộc tính sản phẩm")
                .data(response)
                .build();
    }

    @GetMapping("/{id}")
    public ResponseData<ProductAttributeResponse> getProductAttributeById(@PathVariable Long id) {

        ProductAttributeResponse attribute = productAttributeService.getProductAttribute(id);

        return ResponseData.<ProductAttributeResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Nhận thuộc tính sản phẩm thành công ")
                .data(attribute)
                .build();
    }

    @PostMapping
    public ResponseData<Void> createProductAttribute(@Valid @RequestBody ProductAttributeRequest productAttributeRequest) {

        productAttributeService.createProductAttribute(productAttributeRequest);

        return ResponseData.<Void>builder()
                .status(HttpStatus.CREATED.value())
                .message("Thuộc tính sản phẩm được tạo thành công")
                .build();
    }

    @PutMapping("/{id}")
    public ResponseData<Void> updateProductAttribute(@PathVariable Long id, @Valid @RequestBody ProductAttributeRequest dto) {

        productAttributeService.updateProductAttribute(id, dto);

        return ResponseData.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Thuộc tính sản phẩm được cập nhật thành công")
                .build();

    }

    @DeleteMapping
    public ResponseData<Void> deleteProductAttributes(@RequestBody List<Long> ids) {

        productAttributeService.deleteProductAttributes(ids);

        return ResponseData.<Void>builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message("Xóa thuộc tính sản phẩm thành công")
                .build();
    }

    @GetMapping("/list-name")
    public ResponseData<List<ProductAttributeNameResponse>> getAllNameProductAttributes() {

        List<ProductAttributeNameResponse> names = productAttributeService.getAttributesName();

        return ResponseData.<List<ProductAttributeNameResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Nhận thành công tất cả tên thuộc tính sản phẩm")
                .data(names)
                .build();
    }

}