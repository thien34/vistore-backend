package com.example.back_end.core.admin.product.controller;

import com.example.back_end.core.admin.product.payload.request.ProductAttributeRequest;
import com.example.back_end.core.admin.product.payload.response.ProductAttributeResponse;
import com.example.back_end.core.admin.product.service.ProductAttributeService;
import com.example.back_end.core.common.PageResponse;
import com.example.back_end.core.common.ResponseData;
import com.example.back_end.entity.ProductAttribute;
import com.example.back_end.infrastructure.constant.SuccessCode;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/product-attribute")
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductAttributeController {
    ProductAttributeService productAttributeService;

    @GetMapping
    public ResponseData<PageResponse<ProductAttributeResponse>> getAll(@RequestParam(value = "name", defaultValue = "") String name,
                                                                       @RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
                                                                       @RequestParam(value = "pageSize", defaultValue = "6") int pageSize
    ) {
        PageResponse<ProductAttributeResponse> response = (PageResponse<ProductAttributeResponse>) productAttributeService.getAll(name, pageNo, pageSize);
        return ResponseData.<PageResponse<ProductAttributeResponse>>builder()
                .status(SuccessCode.PRODUCT_ATTRIBUTE_GET_ALL.getStatusCode().value())
                .message(SuccessCode.PRODUCT_ATTRIBUTE_GET_ALL.getMessage())
                .data(response)
                .build();
    }

    @GetMapping("/{id}")
    public ResponseData<ProductAttributeResponse> getProductAttributeById(@PathVariable Long id) {

        ProductAttributeResponse attribute = productAttributeService.getProductAttributeById(id);
        return ResponseData.<ProductAttributeResponse>builder()
                .data(attribute)
                .build();
    }


    @PostMapping
    public ResponseData<ProductAttribute> createProductAttribute(@Valid @RequestBody ProductAttributeRequest dto) {
        return ResponseData.<ProductAttribute>builder()
                .status(HttpStatus.OK.value())
                .message(SuccessCode.PRODUCT_ATTRIBUTE_CREATED.getMessage())
                .data(productAttributeService.createProductAttribute(dto))
                .build();
    }
    @PutMapping("/{id}")
    public ResponseData<ProductAttributeResponse> updateProductAttribute(@PathVariable Long id, @Valid @RequestBody ProductAttributeRequest dto) {
        ProductAttributeResponse updatedAttribute = productAttributeService.updateProductAttribute(id, dto);
        return ResponseData.<ProductAttributeResponse>builder()
                .status(SuccessCode.PRODUCT_ATTRIBUTE_UPDATED.getStatusCode().value())
                .message(SuccessCode.PRODUCT_ATTRIBUTE_UPDATED.getMessage())
                .data(updatedAttribute)
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseData<Void> delete(@PathVariable Long id) {
        productAttributeService.deleteProductAttribute(id);
        return ResponseData.<Void>builder()
                .status(SuccessCode.PRODUCT_ATTRIBUTE_DELETED.getCode())
                .message(SuccessCode.PRODUCT_ATTRIBUTE_DELETED.getMessage())
                .build();
    }
}
