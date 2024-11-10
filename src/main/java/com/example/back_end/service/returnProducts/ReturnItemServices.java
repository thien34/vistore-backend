package com.example.back_end.service.returnProducts;

import com.example.back_end.core.admin.returnProduct.payload.request.ReturnItemRequest;
import com.example.back_end.core.admin.returnProduct.payload.response.ReturnItemResponse;

import java.util.List;

public interface ReturnItemServices {

    ReturnItemResponse addReturnItem(ReturnItemRequest returnItemRequest);

    List<ReturnItemResponse> addReturnItems(List<ReturnItemRequest> returnItemRequests);

    List<ReturnItemResponse> getAllReturnItemsByReturnRequestId(Long returnRequestId);

    ReturnItemResponse getReturnItemById(Long id);
}
