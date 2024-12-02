package com.example.back_end.service.returnProducts;

import com.example.back_end.core.admin.returnProduct.payload.request.ReturnRequestRequest;
import com.example.back_end.core.admin.returnProduct.payload.request.ReturnTimeLineRequest;
import com.example.back_end.core.admin.returnProduct.payload.response.ReturnRequestResponse;
import com.example.back_end.core.admin.returnProduct.payload.response.ReturnTimeLineResponse;
import com.example.back_end.core.common.PageRequest;
import com.example.back_end.core.common.PageResponse1;

import java.util.List;

public interface ReturnRequestServices {

    ReturnRequestResponse createReturnRequest(ReturnRequestRequest request);

    void updateReturnRequest(Long id, ReturnRequestRequest request);

    ReturnRequestResponse getReturnRequestById(Long id);

    ReturnRequestResponse getReturnRequestByOrderId(Long orderId);

    PageResponse1<List<ReturnRequestResponse>> getAllReturnRequests(PageRequest pageRequest);

    PageResponse1<List<ReturnRequestResponse>> getAllReturnRequestsByCustomerId(Long customerId, PageRequest pageRequest);

    void createReturnTimeLine(ReturnTimeLineRequest request);

    void createReturnTimeLines(List<ReturnTimeLineRequest> requests);

    List<ReturnTimeLineResponse> getReturnTimeLineByRequestId(Long requestId);
}
