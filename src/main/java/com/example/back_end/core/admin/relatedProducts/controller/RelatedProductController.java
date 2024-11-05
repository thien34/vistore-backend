package com.example.back_end.core.admin.relatedProducts.controller;

import com.example.back_end.core.admin.relatedProducts.payload.request.RelatedProductRequest;
import com.example.back_end.core.admin.relatedProducts.payload.response.RelatedProductResponse;
import com.example.back_end.service.relatedProducts.RelatedProductServices;
import com.example.back_end.core.common.PageResponse;
import com.example.back_end.core.common.ResponseData;
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
@RequestMapping("/admin/related-product")
public class RelatedProductController {
    private final RelatedProductServices relatedProductServices;

    @GetMapping()
    public ResponseData<PageResponse<List<RelatedProductResponse>>> getAllRelatedProducts(
            @RequestParam("productId") Long productId,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "6") Integer size) {
        PageResponse<List<RelatedProductResponse>> response = relatedProductServices.getAll(productId, page, size);
        return ResponseData.<PageResponse<List<RelatedProductResponse>>>builder()
                .status(HttpStatus.OK.value())
                .message("Get Related Product Of Product ID: " + productId + " successfully")
                .data(response)
                .build();
    }

    @PostMapping()
    public ResponseData<Void> addRelatedProduct(@RequestBody @Valid List<RelatedProductRequest> relatedProductRequests) {
        relatedProductServices.addRelatedProducts(relatedProductRequests);
        return ResponseData.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Add new Related Products successfully")
                .build();
    }

    @DeleteMapping()
    public ResponseData<Void> deleteRelatedProduct(@RequestBody List<Long> ids) {
        relatedProductServices.deleteRelatedProducts(ids);
        return ResponseData.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Delete Related Products successfully")
                .build();
    }

    @PutMapping("/{id}")
    public ResponseData<Void> updateRelatedProduct(@PathVariable("id") Long id, @RequestBody @Valid RelatedProductRequest relatedProductRequest) {
        relatedProductServices.updateRelatedProducts(id, relatedProductRequest);
        return ResponseData.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Update Related Products successfully")
                .build();
    }

}
