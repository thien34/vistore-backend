package com.example.back_end.core.admin.role.mapper;

import com.example.back_end.core.admin.role.payload.request.CustomerRoleRequest;
import com.example.back_end.core.admin.role.payload.response.CustomerRoleResponse;
import com.example.back_end.core.admin.role.payload.response.RoleNameResponse;
import com.example.back_end.entity.CustomerRole;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerRoleMapper {

    CustomerRole toEntity(CustomerRoleRequest dto);

    CustomerRoleResponse toDto(CustomerRole entity);

    List<CustomerRoleResponse> toResponseList(List<CustomerRole> customerRoles);

    RoleNameResponse toRoleNameResponse(CustomerRole entity);

    List<RoleNameResponse> toRoleNameResponseList(List<CustomerRole> customerRoles);

    void updateCustomerRoleFromRequest(CustomerRoleRequest request, @MappingTarget CustomerRole customerRole);

}