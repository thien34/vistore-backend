package com.example.back_end.core.admin.customer.mapper;

import com.example.back_end.core.admin.customer.payload.request.CustomerPasswordRequest;
import com.example.back_end.core.admin.customer.payload.response.CustomerPasswordResponse;
import com.example.back_end.entity.CustomerPassword;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerPasswordMapper {

    @Mapping(source = "customerId", target = "customer.id")
    CustomerPassword toEntity(CustomerPasswordRequest request);

    @Mapping(source = "customer.id", target = "customerId")
    CustomerPasswordResponse toResponse(CustomerPassword customerPassword);

    List<CustomerPasswordResponse> toResponseList(List<CustomerPassword> customerPasswords);

}
