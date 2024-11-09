package com.example.back_end.service.returnProducts.impl;

import com.example.back_end.core.admin.returnProduct.mapper.ReturnRequestMapper;
import com.example.back_end.core.admin.returnProduct.payload.request.ReturnRequestRequest;
import com.example.back_end.core.admin.returnProduct.payload.response.ReturnRequestResponse;
import com.example.back_end.entity.ReturnRequest;
import com.example.back_end.repository.ReturnRequestRepository;
import com.example.back_end.service.returnProducts.ReturnRequestServices;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReturnRequestServicesImpl implements ReturnRequestServices {
    private final ReturnRequestRepository repository;
    private final ReturnRequestMapper mapper;

    @Override
    public ReturnRequestResponse createReturnRequest(ReturnRequestRequest request) {
        ReturnRequest savedReturnRequest = repository.save(mapper.toRequest(request));
        return mapper.toResponse(savedReturnRequest);
    }

    @Override
    public void updateReturnRequest(Long id, ReturnRequestRequest request) {
        ReturnRequest savedReturnRequest = repository.findById(id).orElseThrow(() -> new RuntimeException("Return Request not found with id: " + id));
        mapper.updateReturnRequest(request, savedReturnRequest);
        repository.save(savedReturnRequest);
    }

    @Override
    public ReturnRequestResponse getReturnRequestById(Long id) {
        ReturnRequest ReturnRequest = repository.findById(id).orElseThrow(() -> new RuntimeException("Return Request not found with id: " + id));
        return mapper.toResponse(ReturnRequest);
    }

    @Override
    public ReturnRequestResponse getReturnRequestByOrderId(Long orderId) {
        Optional<ReturnRequest> result = Optional.ofNullable(repository.findByOrderId(orderId));
        ReturnRequest returnRequest = result.orElseThrow(() -> new RuntimeException("Return Request not found with Order id: " + orderId));
        return mapper.toResponse(returnRequest);
    }

    @Override
    public List<ReturnRequestResponse> getAllReturnRequests() {
        return mapper.toResponseList(repository.findAll());
    }

    @Override
    public List<ReturnRequestResponse> getAllReturnRequestsByCustomerId(Long customerId) {
        return mapper.toResponseList(repository.findByCustomerId(customerId));
    }
}
