package com.example.back_end.service.role;

import com.example.back_end.core.admin.role.payload.request.CustomerRoleRequest;
import com.example.back_end.core.admin.role.payload.request.RoleSearchRequest;
import com.example.back_end.core.admin.role.payload.response.CustomerRoleResponse;
import com.example.back_end.core.admin.role.payload.response.RoleNameResponse;
import com.example.back_end.core.common.PageResponse1;

import java.util.List;

public interface CustomerRoleService {

    void createCustomerRole(CustomerRoleRequest request);

    void updateCustomerRole(Long id, CustomerRoleRequest request);

    PageResponse1<List<CustomerRoleResponse>> getAll(RoleSearchRequest searchRequest);

    CustomerRoleResponse getCustomerRole(Long id);

    void deleteCustomerRoles(List<Long> ids);

    List<RoleNameResponse> getAllCustomerRoleName();

}
