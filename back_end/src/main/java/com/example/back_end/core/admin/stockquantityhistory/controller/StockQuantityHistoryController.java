package com.example.back_end.core.admin.stockquantityhistory.controller;

import com.example.back_end.core.admin.stockquantityhistory.payload.request.StockQuantityHistoryRequest;
import com.example.back_end.core.admin.stockquantityhistory.payload.response.StockQuantityHistoryResponse;
import com.example.back_end.core.admin.stockquantityhistory.service.StockQuantityHistoryServices;
import com.example.back_end.core.common.PageResponse;
import com.example.back_end.core.common.ResponseData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/admin/stock-quantity-history")
public class StockQuantityHistoryController {

    private final StockQuantityHistoryServices stockQuantityHistoryServices;

    @GetMapping()
    public ResponseData<PageResponse<List<StockQuantityHistoryResponse>>> getStockQuantityHistory(
            @RequestParam(value = "productId", required = false) Long productId,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "6") Integer size) {

        PageResponse<List<StockQuantityHistoryResponse>> response = stockQuantityHistoryServices
                .getAllHistoryOfProduct(productId, page, size);

        return ResponseData.<PageResponse<List<StockQuantityHistoryResponse>>>builder()
                .status(HttpStatus.OK.value())
                .message("Get StockQuantityHistory successfully")
                .data(response)
                .build();
    }

    @PostMapping
    public ResponseData<Void> create(@RequestBody @Valid StockQuantityHistoryRequest stockQuantityHistoryRequest) {

        stockQuantityHistoryServices.createStockQuantityHistory(stockQuantityHistoryRequest);

        return ResponseData.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Add a record of StockQuantityHistory successfully")
                .build();
    }

    @PutMapping("/{id}")
    public ResponseData<Void> update(@PathVariable Long id, @RequestBody @Valid StockQuantityHistoryRequest stockQuantityHistoryRequest) {

        stockQuantityHistoryServices.updateStockQuantityHistory(id, stockQuantityHistoryRequest);

        return ResponseData.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Update a record of StockQuantityHistory successfully")
                .build();
    }

    @GetMapping("/{id}")
    public ResponseData<StockQuantityHistoryResponse> getARecordOfStocKQuanityHistory(@PathVariable Long id) {
        StockQuantityHistoryResponse response = stockQuantityHistoryServices.getStockQuantityHistory(id);

        return ResponseData.<StockQuantityHistoryResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Get a record of StockQuantityHistory successfully")
                .data(response)
                .build();
    }

}