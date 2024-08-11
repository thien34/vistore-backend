package com.example.back_end.core.admin.stockquantityhistory.service;

import com.example.back_end.core.admin.stockquantityhistory.payload.request.StockQuantityHistoryRequest;
import com.example.back_end.core.admin.stockquantityhistory.payload.response.StockQuantityHistoryResponse;
import com.example.back_end.core.common.PageResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StockQuantityHistoryServices {

    void createStockQuantityHistory(StockQuantityHistoryRequest stockQuantityHistoryRequest);

    void updateStockQuantityHistory(Long stockQuantityHistoryId, StockQuantityHistoryRequest stockQuantityHistoryRequest);

    PageResponse<List<StockQuantityHistoryResponse>> getAllHistoryOfProduct(Long productId, Integer pageNo, Integer pageSize);

    StockQuantityHistoryResponse getStockQuantityHistory(Long stockQuantityHistoryId);

}
