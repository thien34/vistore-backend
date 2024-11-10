package com.example.back_end.service.returnProducts.impl;

import com.example.back_end.core.admin.returnProduct.mapper.ReturnItemMapper;
import com.example.back_end.core.admin.returnProduct.payload.request.ReturnItemRequest;
import com.example.back_end.core.admin.returnProduct.payload.response.ReturnItemResponse;
import com.example.back_end.entity.ReturnItem;
import com.example.back_end.repository.ReturnItemRepository;
import com.example.back_end.service.returnProducts.ReturnItemServices;
import lombok.RequiredArgsConstructor;
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
    public List<ReturnItemResponse> getAllReturnItemsByReturnRequestId(Long returnRequestId) {
        List<ReturnItem> returnItems = repository.findByReturnRequestId(returnRequestId);
        return mapper.maptoResponseList(returnItems);
    }

    @Override
    public ReturnItemResponse getReturnItemById(Long id) {
        ReturnItem returnItem = repository.findById(id).orElseThrow(
                () -> new RuntimeException("ReturnItem not found with id: " + id)
        );
        return mapper.maptoResponse(returnItem);
    }
}
