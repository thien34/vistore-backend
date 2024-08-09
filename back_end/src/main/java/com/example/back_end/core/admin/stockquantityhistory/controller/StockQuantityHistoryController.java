package com.example.back_end.core.admin.stockquantityhistory.controller;

import com.example.back_end.core.admin.stockquantityhistory.payload.request.StockQuantityHistoryRequest;
import com.example.back_end.core.admin.stockquantityhistory.payload.response.StockQuantityHistoryResponse;
import com.example.back_end.core.admin.stockquantityhistory.service.StockQuantityHistoryServices;
import com.example.back_end.core.common.PageResponse;
import com.example.back_end.core.common.ResponseData;
import com.example.back_end.core.common.ResponseError;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/stockquantityhistory")
@RequiredArgsConstructor
@Slf4j
public class StockQuantityHistoryController {
    private final StockQuantityHistoryServices stockQuantityHistoryServices;

    @GetMapping()
    public ResponseData<?> getStockQuanityHistory(
            @RequestParam(value = "productId", required = false) Long productId,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "6") Integer size) {
        try {
            PageResponse<?> response = stockQuantityHistoryServices.getAllHistoryOfProduct(productId, page, size);
            return new ResponseData<>(HttpStatus.OK.value(), "Get StockQuantityHistory successfully", response);
        } catch (Exception e) {
            log.error("Error getting StockQuantityHistory", e);
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

    @PostMapping
    public ResponseData<?> create(@RequestBody @Valid StockQuantityHistoryRequest stockQuantityHistoryRequest, BindingResult bindingResult) {
        log.info("Request add a record of StockQuantityHistory, {}", stockQuantityHistoryRequest);
        try {
            if (bindingResult.hasErrors()) {
                return new ResponseError(HttpStatus.BAD_REQUEST.value(), bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .collect(Collectors.joining(" ; ")));
            }
            stockQuantityHistoryServices.createStockQuantityHistory(stockQuantityHistoryRequest);
            return new ResponseData<>(HttpStatus.OK.value(), "Add a record of StockQuantityHistory successfully");
        } catch (Exception e) {
            log.error("Error adding a record of StockQuantityHistory", e);
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseData<?> update(@PathVariable Long id, @RequestBody @Valid StockQuantityHistoryRequest stockQuantityHistoryRequest, BindingResult bindingResult) {
        log.info("Request to update a record of StockQuantityHistory with id: {}, {}", id, stockQuantityHistoryRequest);
        try {
            if (bindingResult.hasErrors()) {
                return new ResponseError(HttpStatus.BAD_REQUEST.value(), bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .collect(Collectors.joining(" ; ")));
            }
            stockQuantityHistoryServices.updateStockQuantityHistory(id, stockQuantityHistoryRequest);
            return new ResponseData<>(HttpStatus.OK.value(), "Update a record of StockQuantityHistory successfully");
        } catch (Exception e) {
            log.error("Error updating a record of StockQuantityHistory", e);
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseData<?> getARecordOfStocKQuanityHistory(@PathVariable Long id) {
        try {
            StockQuantityHistoryResponse response = stockQuantityHistoryServices.getStockQuanityHistory(id);
            return new ResponseData<>(HttpStatus.OK.value(), "Get a record of StockQuantityHistory successfully", response);
        } catch (Exception e) {
            log.error("Error getting a record of StockQuantityHistory", e);
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }
}

