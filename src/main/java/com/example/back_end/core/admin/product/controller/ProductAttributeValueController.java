package com.example.back_end.core.admin.product.controller;

import com.example.back_end.core.admin.product.payload.response.AttributeValueResponse;
import com.example.back_end.core.common.ResponseData;
import com.example.back_end.service.product.ProductAttributeValueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/attribute-values")
@RequiredArgsConstructor
public class ProductAttributeValueController {

    private final ProductAttributeValueService productAttributeValueService;

    @GetMapping("/")
    public ResponseData<List<AttributeValueResponse>> getAttributeValuesByProductId(
            @RequestParam Long productId,
            @RequestParam(required = false) Long attributeId) {

        List<AttributeValueResponse> responses = productAttributeValueService.getProductAttributeValues(productId, attributeId);

        return ResponseData.<List<AttributeValueResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Get categories name success")
                .data(responses)
                .build();
    }

}
