package com.example.back_end.core.admin.customer.mapper;

import com.example.back_end.core.admin.customer.payload.request.CustomerFullRequest;
import com.example.back_end.core.admin.customer.payload.response.CustomerResponse;
import com.example.back_end.entity.Customer;
import com.example.back_end.entity.CustomerRole;
import com.example.back_end.entity.CustomerRoleMapping;
import java.util.Collections;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    Customer toEntity(CustomerFullRequest request);

    CustomerResponse toResponse(Customer customer);

    List<CustomerResponse> toResponseList(List<Customer> customers);

    @Mapping(target = "customerRoles",ignore = true)
    void updateFromFullRequest(CustomerFullRequest request, @MappingTarget Customer customer);

    default List<CustomerRoleMapping> mapToRoleMappings(List<Long> roleIds) {
        if (roleIds == null) return Collections.emptyList();

        return roleIds.stream()
                .map(roleId -> {
                    CustomerRoleMapping mapping = new CustomerRoleMapping();
                    mapping.setCustomerRole(new CustomerRole(roleId));
                    return mapping;
                })
                .toList();
    }

    default List<Long> mapToRoleIds(List<CustomerRoleMapping> roleMappings) {
        if (roleMappings == null) return Collections.emptyList();

        return roleMappings.stream()
                .map(roleMapping -> roleMapping.getCustomerRole().getId())
                .toList();
    }

}
