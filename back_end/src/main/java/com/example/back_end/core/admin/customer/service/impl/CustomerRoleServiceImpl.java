package com.example.back_end.core.admin.customer.service.impl;

import com.example.back_end.core.admin.customer.mapper.CustomerRoleMapper;
import com.example.back_end.core.admin.customer.payload.request.CustomerRoleRequest;
import com.example.back_end.core.admin.customer.payload.response.CustomerRoleResponse;
import com.example.back_end.core.admin.customer.service.CustomerRoleService;
import com.example.back_end.core.common.PageResponse;
import com.example.back_end.entity.CustomerRole;
import com.example.back_end.infrastructure.constant.ErrorCode;
import com.example.back_end.infrastructure.constant.SortType;
import com.example.back_end.infrastructure.exception.AlreadyExistsException;
import com.example.back_end.infrastructure.exception.NotFoundException;
import com.example.back_end.infrastructure.utils.PageUtils;
import com.example.back_end.repository.CustomerRoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerRoleServiceImpl implements CustomerRoleService {

    CustomerRoleRepository customerRoleRepository;
    CustomerRoleMapper customerRoleMapper;

    private static final Set<String> PROTECTED_ROLES = Set.of("Administrators", "Guests", "Customers", "Employees");

    @Override
    @Transactional
    public CustomerRoleResponse createCustomerRole(CustomerRoleRequest request) {

        // Validate if role name already exists
        validateRoleNameUnique(request.getName());

        CustomerRole customerRole = customerRoleMapper.toEntity(request);
        return customerRoleMapper.toResponse(customerRoleRepository.save(customerRole));
    }

    @Override
    @Transactional
    public void updateCustomerRole(Long id, CustomerRoleRequest request) {

        CustomerRole customerRole = customerRoleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CUSTOMER_ROLE_NOT_FOUND.getMessage()));

        // Allow the `active` status to be updated for protected roles, but prevent renaming
        if (PROTECTED_ROLES.contains(customerRole.getName()) && !customerRole.getName().equals(request.getName()))
            throw new IllegalArgumentException("The system name of system customer roles can't be edited.");

        // Validate if the new name is already in use (excluding current role)
        if (!customerRole.getName().equals(request.getName()))
            validateRoleNameUniqueForUpdate(request.getName(), id);

        // Update the role entity (only name if not protected, and `active`)
        customerRoleMapper.updateCustomerRoleFromRequest(request, customerRole);

        customerRoleRepository.save(customerRole);
    }

    @Override
    public PageResponse<List<CustomerRoleResponse>> getAll(
            String name, Boolean active, Integer pageNo, Integer pageSize
    ) {

        Pageable pageable = PageUtils.createPageable(pageNo, pageSize, "id", SortType.DESC.getValue());

        Page<CustomerRole> customerRolePage = customerRoleRepository.findAll(
                CustomerRoleSpecification.filterByNameAndActive(name, active), pageable);

        List<CustomerRoleResponse> customerRoleResponses = customerRoleMapper
                .toResponseList(customerRolePage.getContent());

        return PageResponse.<List<CustomerRoleResponse>>builder()
                .page(customerRolePage.getNumber())
                .size(customerRolePage.getSize())
                .totalPage(customerRolePage.getTotalPages())
                .items(customerRoleResponses)
                .build();
    }

    @Override
    public CustomerRoleResponse getCustomerRole(Long id) {
        CustomerRole customerRole = findCustomerRoleById(id);
        return customerRoleMapper.toResponse(customerRole);
    }

    private CustomerRole findCustomerRoleById(Long id) {
        return customerRoleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CUSTOMER_ROLE_NOT_FOUND.getMessage()));
    }

    @Override
    @Transactional
    public void deleteCustomerRoles(List<Long> ids) {
        List<CustomerRole> customerRoles = customerRoleRepository.findAllById(ids);

        // Validate if trying to delete protected roles
        for (CustomerRole role : customerRoles) {
            if (PROTECTED_ROLES.contains(role.getName())) {
                throw new IllegalArgumentException("System role could not be deleted");
            }
        }

        if (customerRoles.size() != ids.size()) {
            throw new NotFoundException("One or more customer roles not found for the given ids");
        }

        customerRoleRepository.deleteAll(customerRoles);
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
}


