package com.example.back_end.service.returnProducts.impl;

import com.example.back_end.core.admin.returnProduct.mapper.ReturnItemMapper;
import com.example.back_end.core.admin.returnProduct.payload.request.ReturnItemRequest;
import com.example.back_end.core.admin.returnProduct.payload.response.ReturnItemResponse;
import com.example.back_end.core.common.PageRequest;
import com.example.back_end.core.common.PageResponse1;
import com.example.back_end.entity.ReturnItem;
import com.example.back_end.infrastructure.utils.PageUtils;
import com.example.back_end.repository.ReturnItemRepository;
import com.example.back_end.service.returnProducts.ReturnItemServices;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReturnItemServicesImpl implements ReturnItemServices {

    private final ReturnItemRepository repository;

    private final ReturnItemMapper mapper;

    @Override
    public ReturnItemResponse addReturnItem(ReturnItemRequest returnItemRequest) {
        ReturnItem returnItem = repository.save(mapper.maptoEntity(returnItemRequest));
        return mapper.maptoResponse(returnItem);
    }

    @Override
    public List<ReturnItemResponse> addReturnItems(List<ReturnItemRequest> returnItemRequests) {
        List<ReturnItem> returnItems = repository.saveAll(mapper.maptoEntities(returnItemRequests));
        return mapper.maptoResponseList(returnItems);
    }

    @Override
    public PageResponse1<List<ReturnItemResponse>> getAllReturnItemsByReturnRequestId(Long returnRequestId, PageRequest pageRequest) {
        Pageable pageable = PageUtils.createPageable(
                pageRequest.getPageNo(),
                pageRequest.getPageSize(),
                pageRequest.getSortBy(),
                pageRequest.getSortDir());
        Page<ReturnItem> result = repository.findByReturnRequestId(returnRequestId, pageable);
        List<ReturnItemResponse> responses = mapper.maptoResponseList(result.getContent());
        return PageResponse1.<List<ReturnItemResponse>>builder()
                .totalItems(result.getTotalElements())
                .totalPages(result.getTotalPages())
                .items(responses)
                .build();
    }

    @Override
    public List<ReturnItemResponse> getListReturnItemsByReturnRequestId(Long returnRequestId) {
        return mapper.maptoResponseList(repository.findByReturnRequestId(returnRequestId));
    }

    @Override
    public ReturnItemResponse getReturnItemById(Long id) {
        ReturnItem returnItem = repository.findById(id).orElseThrow(
                () -> new RuntimeException("ReturnItem not found with id: " + id)
        );
        return mapper.maptoResponse(returnItem);
    }
}
