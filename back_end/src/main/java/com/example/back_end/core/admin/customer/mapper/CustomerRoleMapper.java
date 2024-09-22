package com.example.back_end.core.admin.customer.mapper;

import com.example.back_end.core.admin.customer.payload.request.CustomerRoleRequest;
import com.example.back_end.core.admin.customer.payload.response.CustomerRoleResponse;
import com.example.back_end.entity.CustomerRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerRoleMapper {

    @Mapping(target = "id", ignore = true)
    CustomerRole toEntity(CustomerRoleRequest dto);

    CustomerRoleResponse toResponse(CustomerRole entity);

    List<CustomerRoleResponse> toResponseList(List<CustomerRole> customerRoles);

    void updateCustomerRoleFromRequest(CustomerRoleRequest request, @MappingTarget CustomerRole customerRole);

}