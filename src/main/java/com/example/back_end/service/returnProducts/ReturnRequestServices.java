package com.example.back_end.service.returnProducts;

import com.example.back_end.core.admin.returnProduct.payload.request.ReturnRequestRequest;
import com.example.back_end.core.admin.returnProduct.payload.response.ReturnRequestResponse;

import java.util.List;

public interface ReturnRequestServices {

    ReturnRequestResponse createReturnRequest(ReturnRequestRequest request);

    void updateReturnRequest(Long id, ReturnRequestRequest request);

    ReturnRequestResponse getReturnRequestById(Long id);

    ReturnRequestResponse getReturnRequestByOrderId(Long orderId);

    List<ReturnRequestResponse> getAllReturnRequests();

    List<ReturnRequestResponse> getAllReturnRequestsByCustomerId(Long customerId);
}
