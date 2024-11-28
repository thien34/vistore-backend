package com.example.back_end.core.admin.returnProduct.mapper;

import com.example.back_end.core.admin.returnProduct.payload.request.ReturnItemRequest;
import com.example.back_end.core.admin.returnProduct.payload.response.ReturnItemResponse;
import com.example.back_end.entity.ReturnItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReturnItemMapper {

    @Mapping(source = "orderItemId", target = "orderItem.id")
    @Mapping(source = "returnRequestId", target = "returnRequest.id")
    @Mapping(source = "productId", target = "product.id")
    ReturnItem maptoEntity(ReturnItemRequest request);

    @Mapping(source = "orderItemId", target = "orderItem.id")
    @Mapping(source = "returnRequestId", target = "returnRequest.id")
    @Mapping(source = "productId", target = "product.id")
    List<ReturnItem> maptoEntities(List<ReturnItemRequest> requests);


    @Mapping(source = "orderItem.quantity", target = "quantity")
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "product.id", target = "productId")
    ReturnItemResponse maptoResponse(ReturnItem returnItem);


    List<ReturnItemResponse> maptoResponseList(List<ReturnItem> returnItemList);
}
