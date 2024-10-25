package com.example.back_end.service.role.impl;

import com.example.back_end.core.admin.role.mapper.CustomerRoleMapper;
import com.example.back_end.core.admin.role.payload.request.CustomerRoleRequest;
import com.example.back_end.core.admin.role.payload.request.RoleSearchRequest;
import com.example.back_end.core.admin.role.payload.response.CustomerRoleResponse;
import com.example.back_end.core.admin.role.payload.response.RoleNameResponse;
import com.example.back_end.core.common.PageResponse1;
import com.example.back_end.entity.CustomerRole;
import com.example.back_end.infrastructure.constant.ErrorCode;
import com.example.back_end.infrastructure.exception.AlreadyExistsException;
import com.example.back_end.infrastructure.exception.NotFoundException;
import com.example.back_end.infrastructure.exception.RoleDeletionException;
import com.example.back_end.infrastructure.utils.PageUtils;
import com.example.back_end.repository.CustomerRoleRepository;
import com.example.back_end.service.role.CustomerRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomerRoleServiceImpl implements CustomerRoleService {

    private static final Set<String> PROTECTED_ROLES = Set.of("Administrators", "Guests", "Customers", "Employees");
    private final CustomerRoleRepository customerRoleRepository;
    private final CustomerRoleMapper customerRoleMapper;

    @Override
    public void createCustomerRole(CustomerRoleRequest request) {

        validateRoleNameUnique(request.getName());

        CustomerRole customerRole = customerRoleMapper.toEntity(request);
        customerRoleRepository.save(customerRole);
    }

    @Override
    public void updateCustomerRole(Long id, CustomerRoleRequest request) {

        CustomerRole customerRole = customerRoleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CUSTOMER_ROLE_NOT_FOUND.getMessage()));

        if (PROTECTED_ROLES.contains(customerRole.getName()) && !customerRole.getName().equals(request.getName()))
            throw new IllegalArgumentException("The system name of system customer roles can't be edited.");

        if (!customerRole.getName().equals(request.getName())) {
            validateRoleNameUniqueForUpdate(request.getName(), id);
        }

        customerRoleMapper.updateCustomerRoleFromRequest(request, customerRole);

        customerRoleRepository.save(customerRole);
    }

    @Override
    public PageResponse1<List<CustomerRoleResponse>> getAll(RoleSearchRequest searchRequest) {

        Pageable pageable = PageUtils.createPageable(
                searchRequest.getPageNo(),
                searchRequest.getPageSize(),
                searchRequest.getSortBy(),
                searchRequest.getSortDir());

        Page<CustomerRole> customerRolePage = customerRoleRepository.findAll(
                CustomerRoleSpecification.filterByNameAndActive(searchRequest.getName(), searchRequest.getActive()),
                pageable
        );

        List<CustomerRoleResponse> customerRoleResponses = customerRoleMapper
                .toResponseList(customerRolePage.getContent());

        return PageResponse1.<List<CustomerRoleResponse>>builder()
                .totalItems(customerRolePage.getTotalElements())
                .totalPages(customerRolePage.getTotalPages())
                .items(customerRoleResponses)
                .build();
    }

    @Override
    public CustomerRoleResponse getCustomerRole(Long id) {

        CustomerRole customerRole = findCustomerRoleById(id);
        return customerRoleMapper.toDto(customerRole);
    }


    @Override
    @Transactional
    public void deleteCustomerRoles(List<Long> ids) {
        List<CustomerRole> customerRoles = customerRoleRepository.findAllById(ids);

        for (CustomerRole role : customerRoles) {
            if (PROTECTED_ROLES.contains(role.getName()))
                throw new RoleDeletionException(ErrorCode.SYSTEM_ROLE_COULD_NOT_BE_DELETED.getMessage());

        }
        if (customerRoles.size() != ids.size())
            throw new NotFoundException("One or more customer roles not found for the given ids");

        customerRoleRepository.deleteAll(customerRoles);
    }

    @Override
    public List<RoleNameResponse> getAllCustomerRoleName() {

        List<CustomerRole> customerRoles = customerRoleRepository.findAll();
        return customerRoleMapper.toRoleNameResponseList(customerRoles);
    }

    // Method to validate role name uniqueness for create
    private void validateRoleNameUnique(String roleName) {
        boolean exists = customerRoleRepository.existsByName(roleName);
        if (exists) {
            throw new AlreadyExistsException("Role name already exists: " + roleName);
        }
    }

    // Method to validate role name uniqueness for update, excluding the current role
    private void validateRoleNameUniqueForUpdate(String roleName, Long id) {
        boolean exists = customerRoleRepository.existsByNameAndIdNot(roleName, id);
        if (exists) {
            throw new AlreadyExistsException("Role name already exists for another role: " + roleName);
        }
    }

    private CustomerRole findCustomerRoleById(Long id) {
        return customerRoleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CUSTOMER_ROLE_NOT_FOUND.getMessage()));
    }

}


