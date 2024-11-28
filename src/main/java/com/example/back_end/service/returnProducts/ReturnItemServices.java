package com.example.back_end.service.returnProducts;

import com.example.back_end.core.admin.returnProduct.payload.request.ReturnItemRequest;
import com.example.back_end.core.admin.returnProduct.payload.response.ReturnItemResponse;
import com.example.back_end.core.common.PageRequest;
import com.example.back_end.core.common.PageResponse1;

import java.util.List;

public interface ReturnItemServices {

    ReturnItemResponse addReturnItem(ReturnItemRequest returnItemRequest);

    List<ReturnItemResponse> addReturnItems(List<ReturnItemRequest> returnItemRequests);

    PageResponse1<List<ReturnItemResponse>> getAllReturnItemsByReturnRequestId(Long returnRequestId, PageRequest pageRequest);

    List<ReturnItemResponse> getListReturnItemsByReturnRequestId(Long returnRequestId);

    ReturnItemResponse getReturnItemById(Long id);
}
