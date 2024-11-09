package com.example.back_end.service.returnProducts.impl;

import com.example.back_end.core.admin.returnProduct.mapper.ReturnInvoiceMapper;
import com.example.back_end.core.admin.returnProduct.payload.request.ReturnInvoiceRequest;
import com.example.back_end.core.admin.returnProduct.payload.response.ReturnInvoiceResponse;
import com.example.back_end.entity.ReturnInvoice;
import com.example.back_end.repository.ReturnInvoiceRepository;
import com.example.back_end.service.returnProducts.ReturnInvoiceServices;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReturnInvoiceServicesImpl implements ReturnInvoiceServices {
    private final ReturnInvoiceRepository repository;
    private final ReturnInvoiceMapper mapper;

    @Override
    public ReturnInvoiceResponse saveReturnInvoice(ReturnInvoiceRequest returnInvoiceRequest) {
        ReturnInvoice returnInvoice =repository.save(mapper.mapReturnInvoice(returnInvoiceRequest)) ;
        return mapper.mapReturnInvoiceResponse(returnInvoice);
    }

    @Override
    public ReturnInvoiceResponse getReturnInvoiceById(Long id) {
        ReturnInvoice returnInvoice = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Return Invoice not found with id: " + id));
        return mapper.mapReturnInvoiceResponse(returnInvoice);
    }

    @Override
    public ReturnInvoiceResponse getReturnInvoiceByOrderId(Long orderId) {
        Optional<ReturnInvoice> result = Optional.ofNullable(repository.findByOrderId(orderId));
        ReturnInvoice returnInvoice= result.orElseThrow(() -> new RuntimeException("Return Invoice not found with id: " + orderId));
        return mapper.mapReturnInvoiceResponse(returnInvoice);
    }

    @Override
    public List<ReturnInvoiceResponse> getAllReturnInvoices() {
        return mapper.mapReturnInvoices(repository.findAll());
    }
}
