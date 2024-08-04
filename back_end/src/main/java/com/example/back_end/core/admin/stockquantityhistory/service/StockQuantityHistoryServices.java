package com.example.back_end.core.admin.stockquantityhistory.service;

import com.example.back_end.core.admin.stockquantityhistory.payload.request.StockQuantityHistoryRequest;
import com.example.back_end.core.admin.stockquantityhistory.payload.response.StockQuantityHistoryResponse;
import com.example.back_end.core.common.PageResponse;
import org.springframework.stereotype.Service;

@Service
public interface StockQuantityHistoryServices {
    void createStockQuantityHistory(StockQuantityHistoryRequest stockQuanityHistoryRequest);

    void updateStockQuantityHistory(Long stockQuanityHistoryId, StockQuantityHistoryRequest stockQuanityHistoryRequest);

    PageResponse<?> getAllHistoryOfProduct(Long productId, Integer pageNo, Integer pageSize);

    StockQuantityHistoryResponse getStockQuanityHistory(Long stockQuantityHistoryId);
}
