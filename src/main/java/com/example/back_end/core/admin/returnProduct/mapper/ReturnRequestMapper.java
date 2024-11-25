package com.example.back_end.core.admin.returnProduct.mapper;

import com.example.back_end.core.admin.returnProduct.payload.request.ReturnRequestRequest;
import com.example.back_end.core.admin.returnProduct.payload.response.ReturnRequestResponse;
import com.example.back_end.entity.ReturnRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReturnRequestMapper {
    @Mapping(target = "customer.id", source = "customerId")
    @Mapping(target = "order.id", source = "orderId")
    ReturnRequest toRequest(ReturnRequestRequest request);

    @Mapping(source = "customer.firstName", target = "firstName")
    @Mapping(source = "customer.lastName", target = "lastName")
    @Mapping(source = "order.id", target = "orderId")
    ReturnRequestResponse toResponse(ReturnRequest returnRequest);

    List<ReturnRequestResponse> toResponseList(List<ReturnRequest> returnRequestList);

    void updateReturnRequest(ReturnRequestRequest request, @MappingTarget ReturnRequest returnRequest);
}
