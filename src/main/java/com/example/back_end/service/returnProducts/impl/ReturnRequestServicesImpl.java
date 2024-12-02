package com.example.back_end.service.returnProducts.impl;

import com.example.back_end.core.admin.returnProduct.mapper.ReturnRequestMapper;
import com.example.back_end.core.admin.returnProduct.mapper.ReturnTimeLineMapper;
import com.example.back_end.core.admin.returnProduct.payload.request.ReturnRequestRequest;
import com.example.back_end.core.admin.returnProduct.payload.request.ReturnTimeLineRequest;
import com.example.back_end.core.admin.returnProduct.payload.response.ReturnRequestResponse;
import com.example.back_end.core.admin.returnProduct.payload.response.ReturnTimeLineResponse;
import com.example.back_end.core.common.PageRequest;
import com.example.back_end.core.common.PageResponse1;
import com.example.back_end.entity.ReturnRequest;
import com.example.back_end.entity.ReturnTimeLine;
import com.example.back_end.infrastructure.utils.PageUtils;
import com.example.back_end.repository.ReturnRequestRepository;
import com.example.back_end.repository.ReturnTimeLineRepository;
import com.example.back_end.service.returnProducts.ReturnRequestServices;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReturnRequestServicesImpl implements ReturnRequestServices {
    private final ReturnRequestRepository repository;
    private final ReturnTimeLineRepository timeLineRepository;
    private final ReturnTimeLineMapper timeLineMapper;
    private final ReturnRequestMapper mapper;
    @Override
    public ReturnRequestResponse createReturnRequest(ReturnRequestRequest request) {
        ReturnRequest savedReturnRequest = repository.save(mapper.toRequest(request));
        ReturnTimeLineRequest timeLineRequest= timeLineMapper.mapToReturnTimeLineRequest(savedReturnRequest);
        timeLineRepository.save(timeLineMapper.mapToReturnTimeLine(timeLineRequest));
        return mapper.toResponse(savedReturnRequest);
    }

    @Override
    public void updateReturnRequest(Long id, ReturnRequestRequest request) {
        ReturnRequest savedReturnRequest = repository.findById(id).orElseThrow(() -> new RuntimeException("Return Request not found with id: " + id));
        mapper.updateReturnRequest(request, savedReturnRequest);
        ReturnTimeLineRequest timeLineRequest= timeLineMapper.mapToReturnTimeLineRequest(savedReturnRequest);
        timeLineRepository.save(timeLineMapper.mapToReturnTimeLine(timeLineRequest));
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
    public PageResponse1<List<ReturnRequestResponse>> getAllReturnRequests(PageRequest pageRequest) {
        Pageable pageable = PageUtils.createPageable(
                pageRequest.getPageNo(),
                pageRequest.getPageSize(),
                pageRequest.getSortBy(),
                pageRequest.getSortDir());
        Page<ReturnRequest> result = repository.findAll(pageable);
        List<ReturnRequestResponse> responses = mapper.toResponseList(result.getContent());
        return PageResponse1.<List<ReturnRequestResponse>>builder()
                .totalItems(result.getTotalElements())
                .totalPages(result.getTotalPages())
                .items(responses)
                .build();
    }

    @Override
    public PageResponse1<List<ReturnRequestResponse>> getAllReturnRequestsByCustomerId(Long customerId, PageRequest pageRequest) {
        Pageable pageable = PageUtils.createPageable(
                pageRequest.getPageNo(),
                pageRequest.getPageSize(),
                pageRequest.getSortBy(),
                pageRequest.getSortDir());
        Page<ReturnRequest> result = repository.findByCustomerId(customerId,pageable);
        List<ReturnRequestResponse> responses = mapper.toResponseList(result.getContent());
        return PageResponse1.<List<ReturnRequestResponse>>builder()
                .totalItems(result.getTotalElements())
                .totalPages(result.getTotalPages())
                .items(responses)
                .build();
    }

    @Override
    public void createReturnTimeLine(ReturnTimeLineRequest request) {
        ReturnTimeLine returnTimeLine = timeLineMapper.mapToReturnTimeLine(request);
        timeLineRepository.save(returnTimeLine);
    }

    @Override
    public void createReturnTimeLines(List<ReturnTimeLineRequest> requests) {
        List<ReturnTimeLine> returnTimeLines = timeLineMapper.mapToReturnTimeLines(requests);
        timeLineRepository.saveAll(returnTimeLines);
    }

    @Override
    public List<ReturnTimeLineResponse> getReturnTimeLineByRequestId(Long requestId) {
        List<ReturnTimeLine> result = timeLineRepository.getAllReturnTimeLinesByReturnRequestId(requestId);
        return  timeLineMapper.mapToReturnTimeLineResponses(result);
    }
}
