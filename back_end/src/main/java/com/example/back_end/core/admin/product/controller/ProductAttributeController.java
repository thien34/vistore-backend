package com.example.back_end.core.admin.product.controller;

import com.example.back_end.core.admin.product.payload.request.ProductAttributeRequest;
import com.example.back_end.core.admin.product.payload.response.ProductAttributeResponse;
import com.example.back_end.core.admin.product.service.ProductAttributeService;
import com.example.back_end.core.common.PageResponse;
import com.example.back_end.core.common.ResponseData;
import com.example.back_end.core.common.ResponseError;
import com.example.back_end.entity.ProductAttribute;
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
@RequiredArgsConstructor
@RequestMapping("/admin/product-attributes")
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductAttributeController {
    ProductAttributeService productAttributeService;

    @GetMapping
    public ResponseData<PageResponse<ProductAttributeResponse>> getAll(@RequestParam(value = "name", defaultValue = "") String name,
                                                                       @RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
                                                                       @RequestParam(value = "pageSize", defaultValue = "6") int pageSize
    ) {
            PageResponse<ProductAttributeResponse> response = (PageResponse<ProductAttributeResponse>) productAttributeService.getAllProductAttribute(name, pageNo, pageSize);
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
                    .status(SuccessCode.GET_PRODUCT_ATTRIBUTE_BY_ID.getStatusCode().value())
                    .message(SuccessCode.GET_PRODUCT_ATTRIBUTE_BY_ID.getMessage())
                    .data(attribute)
                    .build();
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
            log.error("Error create product attributes", e);
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseData<ProductAttributeResponse> updateProductAttribute(@PathVariable Long id, @Valid @RequestBody ProductAttributeRequest dto) {
        try {
            ProductAttributeResponse updatedAttribute = productAttributeService.updateProductAttribute(id, dto);
            return ResponseData.<ProductAttributeResponse>builder()
                    .status(HttpStatus.OK.value())
                    .message(SuccessCode.PRODUCT_ATTRIBUTE_UPDATED.getMessage())
                    .data(updatedAttribute)
                    .build();
        }catch (Exception e) {
            log.error("Error update product attributes", e);
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }
    @GetMapping("/search")
    public ResponseData<PageResponse<?>> searchByNameName(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        try {
            PageResponse<?> attributes = productAttributeService.searchByNameName(name, page, size);
            return ResponseData.<PageResponse<?>>builder()
                    .status(HttpStatus.OK.value())
                    .message("Product attributes search successfully")
                    .data(attributes)
                    .build();
        } catch (Exception e) {
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }
    @Operation(method = "DELETE", summary = "Delete product attributes",
            description = "Send a request via this API to delete product attributes")
    @DeleteMapping
    public ResponseData<ResponseError> deleteProductAttributes(@RequestBody List<Long> ids) {
        log.info("Request to delete product attributes with ids: {}", ids);
        try {
            productAttributeService.deleteProductAttribute(ids);
            return new ResponseData<>(HttpStatus.OK.value(), "Delete product attributes success");
        } catch (Exception e) {
            log.error("Error deleting product attributes", e);
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }


}