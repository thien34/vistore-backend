package com.example.back_end.core.admin.returnProduct.mapper;


import com.example.back_end.core.admin.returnProduct.payload.request.ProcessedReturnItemRequest;
import com.example.back_end.core.admin.returnProduct.payload.response.ProcessedReturnItemResponse;
import com.example.back_end.entity.ProcessedReturnItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProcessedReturnItemMapper {
    @Mapping(source="returnRequest.id",target = "returnRequestId")
    ProcessedReturnItemResponse toProcessReturnItemResponse(ProcessedReturnItem request);

    List<ProcessedReturnItemResponse> toProcessReturnItemResponses(List<ProcessedReturnItem> request);

    @Mapping(source = "returnRequestId", target = "returnRequest.id")
    ProcessedReturnItem toProcessedReturnItem(ProcessedReturnItemRequest request);

}
