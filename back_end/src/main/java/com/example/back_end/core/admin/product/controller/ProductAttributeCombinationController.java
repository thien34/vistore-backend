package com.example.back_end.core.admin.product.controller;


import com.example.back_end.core.admin.product.payload.request.ProductAttributeCombinationRequest;
import com.example.back_end.core.admin.product.service.ProductAttributeCombinationService;
import com.example.back_end.core.common.ResponseData;
import com.example.back_end.core.common.ResponseError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/product-attribute-combinations")
@Slf4j
public class ProductAttributeCombinationController {

    private final ProductAttributeCombinationService productAttributeCombinationService;

    @PostMapping
    public ResponseData<?> create(@RequestBody ProductAttributeCombinationRequest request) {

        log.info("Request add product combination, {}", request);

        try {
            productAttributeCombinationService.saveOrUpdateProductAttributeCombination(request);
            return new ResponseData<>(HttpStatus.OK.value(), "Add product combination success");
        } catch (Exception e) {
            log.error("Error adding product combination ", e);
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }

    }


    @GetMapping("/product/{productId}")
    public ResponseData<?> findByProductId(@PathVariable("productId") Long productId) {

        log.info("Request find product combination by productId {}", productId);

        try {
            return ResponseData.builder()
                    .status(HttpStatus.OK.value())
                    .data(productAttributeCombinationService.getByProductId(productId))
                    .message("Fetch product combinations successfully")
                    .build();

        } catch (Exception e) {
            log.error("Error fetch product combination ", e);
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseData<?> delete(@PathVariable Long id) {
        log.info("Request delete product combination by id {}", id);
        try {
            productAttributeCombinationService.delete(id);
            return new ResponseData<>(HttpStatus.OK.value(), "Delete product combination success");
        } catch (Exception e) {
            log.error("Error delete product combination ", e);
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }


}
