package com.example.back_end.core.admin.stockquantityhistory.service.impl;

import com.example.back_end.core.admin.stockquantityhistory.mapper.StockQuantityHistoryMapper;
import com.example.back_end.core.admin.stockquantityhistory.payload.request.StockQuantityHistoryRequest;
import com.example.back_end.core.admin.stockquantityhistory.payload.response.StockQuantityHistoryResponse;
import com.example.back_end.core.admin.stockquantityhistory.service.StockQuantityHistoryServices;
import com.example.back_end.core.common.PageResponse;
import com.example.back_end.entity.StockQuantityHistory;
import com.example.back_end.infrastructure.exception.ResourceNotFoundException;
import com.example.back_end.repository.StockQuantityHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockQuantityHistoryServiceImpl implements StockQuantityHistoryServices {
    private final StockQuantityHistoryMapper stockQuantityHistoryMapper;
    private final StockQuantityHistoryRepository stockQuantityHistoryRepository;

    @Override
    public void createStockQuantityHistory(StockQuantityHistoryRequest stockQuantityHistoryRequest) {
        StockQuantityHistory stockQuantityHistory = stockQuantityHistoryMapper.mapStockQuantityHistory(stockQuantityHistoryRequest);
        stockQuantityHistoryRepository.save(stockQuantityHistory);
    }

    @Override
    public void updateStockQuantityHistory(Long stockQuanityHistoryId, StockQuantityHistoryRequest stockQuanityHistoryRequest) {
        StockQuantityHistory stockQuantityHistory = stockQuantityHistoryRepository.findById(stockQuanityHistoryId).orElseThrow(
                () -> new ResourceNotFoundException("Stock Quanity History with id not found: " + stockQuanityHistoryId)
        );
        stockQuantityHistoryMapper.updateStockQuantityHistory(stockQuanityHistoryRequest, stockQuantityHistory);
        stockQuantityHistoryRepository.save(stockQuantityHistory);
    }

    @Override
    public PageResponse<?> getAllHistoryOfProduct(Long productId, Integer pageNo, Integer pageSize) {
        if (pageNo - 1 < 0 || pageSize <= 0) {
            throw new IllegalArgumentException("Invalid page number or page size");
        }
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by("id").descending());
        Page<StockQuantityHistory> stockQuantityHistoryPage = stockQuantityHistoryRepository.findAll(productId, pageable);
        List<StockQuantityHistoryResponse> stockQuanityHistories = stockQuantityHistoryPage.stream()
                .map(stockQuantityHistoryMapper::mapStockQuanityHistoryToStockQuanityHistoryResponse)
                .toList();
        return PageResponse.builder()
                .page(stockQuantityHistoryPage.getNumber() + 1)
                .size(stockQuantityHistoryPage.getSize())
                .totalPage(stockQuantityHistoryPage.getTotalPages())
                .items(stockQuanityHistories)
                .build();
    }
    

    @Override
    public StockQuantityHistoryResponse getStockQuanityHistory(Long stockQuanityHistoryId) {
        StockQuantityHistory stockQuantityHistory = stockQuantityHistoryRepository.findById(stockQuanityHistoryId).orElseThrow(
                () -> new ResourceNotFoundException("Stock Quanity History with id not found: " + stockQuanityHistoryId)
        );
        return stockQuantityHistoryMapper.mapStockQuanityHistoryToStockQuanityHistoryResponse(stockQuantityHistory);
    }

}
