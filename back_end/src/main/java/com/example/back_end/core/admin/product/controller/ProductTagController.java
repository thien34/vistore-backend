package com.example.back_end.core.admin.product.controller;

import com.example.back_end.core.admin.product.payload.request.ProductTagRequestDto;
import com.example.back_end.core.admin.product.service.ProductTagService;
import com.example.back_end.core.common.PageResponse;
import com.example.back_end.core.common.ResponseData;
import com.example.back_end.core.common.ResponseError;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product-tags")
@Slf4j
public class ProductTagController {

    private final ProductTagService productTagService;

    @Operation(method = "GET", summary = "Get all product tags", description = "Send a request via this API to get all product tags")
    @GetMapping
    public ResponseData<?> getAll(@RequestParam(value = "name", defaultValue = "") String name,
                                  @RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
                                  @RequestParam(value = "pageSize", defaultValue = "6") int pageSize,
                                  @RequestParam(value = "sortBy", defaultValue = "id") String sortBy
    ) {
        try {
            PageResponse<?> response = productTagService.getAll(name, pageNo, pageSize);
            return new ResponseData<>(HttpStatus.OK.value(), "Get product tags success", response);
        } catch (Exception e) {
            log.error("Error getting product tags", e);
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

    @Operation(method = "POST", summary = "Add new product tag", description = "Send a request via this API to create new product tag")
    @PostMapping
    public ResponseData<?> create(@Valid @RequestBody ProductTagRequestDto request) {

        log.info("Request add product tag, {}", request);
        try {
            productTagService.createProductTag(request);
            return new ResponseData<>(HttpStatus.OK.value(), "Add product tag success");
        } catch (Exception e) {
            log.error("Error adding product tag", e);
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

    @Operation(method = "DELETE", summary = "Delete product tags", description = "Send a request via this API to delete product tags")
    @DeleteMapping
    public ResponseData<?> delete(@RequestBody List<Long> ids) {
        log.info("Request to delete product tags with ids: {}", ids);
        try {
            productTagService.delete(ids);
            return new ResponseData<>(HttpStatus.OK.value(), "Delete product tags success");
        } catch (Exception e) {
            log.error("Error deleting product tags", e);
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

}
