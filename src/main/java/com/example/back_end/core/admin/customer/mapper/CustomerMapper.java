package com.example.back_end.core.admin.customer.mapper;

import com.example.back_end.core.admin.customer.payload.request.CustomerFullRequest;
import com.example.back_end.core.admin.customer.payload.response.CustomerFullResponse;
import com.example.back_end.core.admin.customer.payload.response.CustomerResponse;
import com.example.back_end.entity.Customer;
import com.example.back_end.entity.CustomerRole;
import com.example.back_end.entity.CustomerRoleMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerMapper {

    @Mapping(source = "customerRoles", target = "customerRoles", qualifiedByName = "mapToRoleMappings")
    Customer toEntity(CustomerFullRequest request);

    @Mapping(source = "customerRoles", target = "customerRoles", qualifiedByName = "mapToRoleNames")
    CustomerResponse toResponse(Customer customer);

    @Mapping(source = "customerRoles", target = "customerRoles", qualifiedByName = "mapToRoleIds")
    CustomerFullResponse toFullResponse(Customer customer);

    List<CustomerResponse> toResponseList(List<Customer> customers);

    @Mapping(target = "customerRoles", ignore = true)
    void updateFromFullRequest(CustomerFullRequest request, @MappingTarget Customer customer);

    @Named("mapToRoleMappings")
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

    @Named("mapToRoleIds")
    default List<Long> mapToRoleIds(List<CustomerRoleMapping> roleMappings) {
        if (roleMappings == null) return Collections.emptyList();

        return roleMappings.stream()
                .map(roleMapping -> roleMapping.getCustomerRole().getId())
                .toList();
    }

    @Named("mapToRoleNames")
    default List<String> mapToRoleNames(List<CustomerRoleMapping> roleMappings) {
        if (roleMappings == null) return Collections.emptyList();

        return roleMappings.stream()
                .map(roleMapping -> roleMapping.getCustomerRole().getName())
                .toList();
    }

}

