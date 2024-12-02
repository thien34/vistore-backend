package com.example.back_end.service.returnProducts;

import com.example.back_end.core.admin.returnProduct.payload.request.PendingReturnItemRequest;
import com.example.back_end.core.admin.returnProduct.payload.request.ProcessedReturnItemRequest;
import com.example.back_end.core.admin.returnProduct.payload.response.PendingReturnItemResponse;
import com.example.back_end.core.admin.returnProduct.payload.response.ProcessedReturnItemResponse;
import com.example.back_end.core.common.PageRequest;
import com.example.back_end.core.common.PageResponse1;

import java.util.List;

public interface ReturnProductServices {
    List<ProcessedReturnItemResponse> createProcessReturnItem(List<ProcessedReturnItemRequest> request);

    PageResponse1<List<ProcessedReturnItemResponse>> getProcessReturnItems(PageRequest pageRequest);

    void createPendingReturnItem(List<PendingReturnItemRequest> pendingReturnItems);

    PageResponse1<List<PendingReturnItemResponse>> getPendingReturnItems(PageRequest pageRequest);

    void createReturnTimeLine();
}
