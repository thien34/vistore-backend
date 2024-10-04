package com.example.back_end.service.customer;

import com.example.back_end.core.admin.customer.payload.request.CustomerRoleRequest;
import com.example.back_end.core.admin.customer.payload.response.CustomerRoleResponse;
import com.example.back_end.core.common.PageResponse;

import java.util.List;

public interface CustomerRoleService {

    CustomerRoleResponse createCustomerRole(CustomerRoleRequest request);

    CustomerRoleResponse getCustomerRole(Long id);

    PageResponse<List<CustomerRoleResponse>> getAll(String name, Boolean active, Integer pageNo, Integer pageSize);

    void updateCustomerRole(Long id, CustomerRoleRequest request);

    void deleteCustomerRoles(List<Long> ids);

    void deleteCustomerRole(Long id);

    List<CustomerRoleResponse> getAllCustomerRoleName();

}
