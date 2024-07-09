package com.example.back_end.core.admin.product.controller;

import com.example.back_end.core.admin.product.payload.request.ProductAttributeRequest;
import com.example.back_end.core.admin.product.payload.response.ProductAttributeResponse;
import com.example.back_end.core.admin.product.service.ProductAttributeService;
import com.example.back_end.core.common.PageResponse;
import com.example.back_end.core.common.ResponseData;
import com.example.back_end.core.common.ResponseError;
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
        try {
            PageResponse<ProductAttributeResponse> response =
                    (PageResponse<ProductAttributeResponse>) productAttributeService.getAll(name, pageNo, pageSize);
            return ResponseData.<PageResponse<ProductAttributeResponse>>builder()
                    .status(SuccessCode.PRODUCT_ATTRIBUTE_GET_ALL.getStatusCode().value())
                    .message(SuccessCode.PRODUCT_ATTRIBUTE_GET_ALL.getMessage())
                    .data(response)
                    .build();
        } catch (Exception e) {
            log.error("Error get all product attribute", e);
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseData<ProductAttributeResponse> getProductAttributeById(@PathVariable Long id) {
        try {
            ProductAttributeResponse attribute = productAttributeService.getProductAttributeById(id);
            return ResponseData.<ProductAttributeResponse>builder()
                    .data(attribute)
                    .build();
        } catch (Exception e) {
            log.error("Error get product attribute by id", e);
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }


    @PostMapping
    public ResponseData<ProductAttribute> createProductAttribute(@Valid @RequestBody ProductAttributeRequest dto) {
        try {
            return ResponseData.<ProductAttribute>builder()
                    .status(HttpStatus.OK.value())
                    .message(SuccessCode.PRODUCT_ATTRIBUTE_CREATED.getMessage())
                    .data(productAttributeService.createProductAttribute(dto))
                    .build();
        }catch (Exception e) {
            log.error("Error create product attribute", e);
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseData<ProductAttributeResponse> updateProductAttribute(@PathVariable Long id, @Valid @RequestBody ProductAttributeRequest dto) {
        try {
            ProductAttributeResponse updatedAttribute = productAttributeService.updateProductAttribute(id, dto);
            return ResponseData.<ProductAttributeResponse>builder()
                    .status(SuccessCode.PRODUCT_ATTRIBUTE_UPDATED.getStatusCode().value())
                    .message(SuccessCode.PRODUCT_ATTRIBUTE_UPDATED.getMessage())
                    .data(updatedAttribute)
                    .build();
        }catch (Exception e) {
            log.error("Error update product attribute", e);
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseData<Void> delete(@PathVariable Long id) {
        try {
            productAttributeService.deleteProductAttribute(id);
            return ResponseData.<Void>builder()
                    .status(SuccessCode.PRODUCT_ATTRIBUTE_DELETED.getCode())
                    .message(SuccessCode.PRODUCT_ATTRIBUTE_DELETED.getMessage())
                    .build();
        }catch (Exception e) {
            log.error("Error delete product attribute", e);
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }
}
