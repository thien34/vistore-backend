package com.example.back_end.service.returnProducts.impl;

import com.example.back_end.core.admin.returnProduct.mapper.PendingReturnItemMapper;
import com.example.back_end.core.admin.returnProduct.mapper.ProcessedReturnItemMapper;
import com.example.back_end.core.admin.returnProduct.payload.request.PendingReturnItemRequest;
import com.example.back_end.core.admin.returnProduct.payload.request.ProcessedReturnItemRequest;
import com.example.back_end.core.admin.returnProduct.payload.response.PendingReturnItemResponse;
import com.example.back_end.core.admin.returnProduct.payload.response.ProcessedReturnItemResponse;
import com.example.back_end.core.common.PageRequest;
import com.example.back_end.core.common.PageResponse1;
import com.example.back_end.entity.PendingReturnItem;
import com.example.back_end.entity.ProcessedReturnItem;
import com.example.back_end.infrastructure.utils.PageUtils;
import com.example.back_end.repository.PendingReturnItemRepository;
import com.example.back_end.repository.ProcessedReturnItemRepository;
import com.example.back_end.service.order.OrderService;
import com.example.back_end.service.returnProducts.ReturnProductServices;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReturnProductServicesImpl implements ReturnProductServices {

    private final ProcessedReturnItemRepository processedReturnItemRepository;
    private final ProcessedReturnItemMapper processedReturnItemMapper;
    private final PendingReturnItemMapper pendingReturnItemMapper;
    private final PendingReturnItemRepository pendingReturnItemRepository;
    private final OrderService orderService;
    @Override
    public List<ProcessedReturnItemResponse> createProcessReturnItem(List<ProcessedReturnItemRequest> request) {
        List<ProcessedReturnItem> processedReturnItems = new ArrayList<>();
        request.forEach( returnItem -> {
            ProcessedReturnItem processedRequest = processedReturnItemMapper.toProcessedReturnItem(returnItem);
            processedRequest.setProductJson(orderService.getProductJsonByOrderId(returnItem.getOrderItemId()));
            processedReturnItems.add(processedRequest);
        });
        return processedReturnItemMapper.toProcessReturnItemResponses(processedReturnItemRepository.saveAll(processedReturnItems));
    }

    @Override
    public PageResponse1<List<ProcessedReturnItemResponse>> getProcessReturnItems(PageRequest pageRequest) {
        Pageable pageable = PageUtils.createPageable(
                pageRequest.getPageNo(),
                pageRequest.getPageSize(),
                pageRequest.getSortBy(),
                pageRequest.getSortDir());
        Page<ProcessedReturnItem> result = processedReturnItemRepository.findAll(pageable);
        List<ProcessedReturnItemResponse> responses = processedReturnItemMapper.toProcessReturnItemResponses(result.getContent());
        return PageResponse1.<List<ProcessedReturnItemResponse>>builder()
                .totalItems(result.getTotalElements())
                .totalPages(result.getTotalPages())
                .items(responses)
                .build();
    }

    @Override
    public void createPendingReturnItem(List<PendingReturnItemRequest> pendingReturnItems) {
        List<PendingReturnItem> pendingReturnItemList = new ArrayList<>();
        pendingReturnItems.forEach(returnItem ->{
            String productJson = orderService.getProductJsonByOrderId(returnItem.getOrderItemId());
            PendingReturnItem request = pendingReturnItemMapper.mapPendingReturnItemRequest(returnItem);
            request.setProductJson(productJson);
            pendingReturnItemList.add(request);
        });
        pendingReturnItemRepository.saveAll(pendingReturnItemList);
    }

    @Override
    public PageResponse1<List<PendingReturnItemResponse>> getPendingReturnItems(PageRequest pageRequest) {
        Pageable pageable = PageUtils.createPageable(
                pageRequest.getPageNo(),
                pageRequest.getPageSize(),
                pageRequest.getSortBy(),
                pageRequest.getSortDir());
        Page<PendingReturnItem> result = pendingReturnItemRepository.findAll(pageable);
        List<PendingReturnItemResponse> responses = pendingReturnItemMapper.mapPendingReturnItemResponse(result.getContent());
        return PageResponse1.<List<PendingReturnItemResponse>>builder()
                .totalItems(result.getTotalElements())
                .totalPages(result.getTotalPages())
                .items(responses)
                .build();
    }
}
