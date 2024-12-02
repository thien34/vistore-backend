package com.example.back_end.core.admin.returnProduct.mapper;

import com.example.back_end.core.admin.returnProduct.payload.request.PendingReturnItemRequest;
import com.example.back_end.core.admin.returnProduct.payload.response.PendingReturnItemResponse;
import com.example.back_end.entity.PendingReturnItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PendingReturnItemMapper {
    @Mapping(source="returnRequest.id",target = "returnRequestId")
    PendingReturnItemResponse mapPendingReturnItemResponse(PendingReturnItem pendingReturnItem);

    List<PendingReturnItemResponse> mapPendingReturnItemResponse(List<PendingReturnItem> pendingReturnItems);

    @Mapping(source="returnRequestId",target = "returnRequest.id")
    PendingReturnItem mapPendingReturnItemRequest(PendingReturnItemRequest request);

}
