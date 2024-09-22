package com.example.back_end.core.admin.customer.service.impl;

import com.example.back_end.core.admin.customer.mapper.CustomerRoleMapper;
import com.example.back_end.core.admin.customer.payload.request.CustomerRoleRequest;
import com.example.back_end.core.admin.customer.payload.response.CustomerRoleResponse;
import com.example.back_end.core.admin.customer.service.CustomerRoleService;
import com.example.back_end.core.common.PageResponse;
import com.example.back_end.entity.CustomerRole;
import com.example.back_end.infrastructure.constant.ErrorCode;
import com.example.back_end.infrastructure.constant.SortType;
import com.example.back_end.infrastructure.exception.NotFoundException;
import com.example.back_end.infrastructure.exception.ResourceNotFoundException;
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

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerRoleServiceImpl implements CustomerRoleService {

    CustomerRoleRepository customerRoleRepository;
    CustomerRoleMapper customerRoleMapper;

    @Override
    @Transactional
    public CustomerRoleResponse createCustomerRole(CustomerRoleRequest request) {

        CustomerRole customerRole = customerRoleMapper.toEntity(request);

        return customerRoleMapper.toResponse(customerRoleRepository.save(customerRole));

    }

    @Override
    @Transactional
    public void updateCustomerRole(Long id, CustomerRoleRequest request) {

        CustomerRole customerRole = customerRoleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CUSTOMER_ROLE_NOT_FOUND.getMessage()));

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

        if (customerRoles.size() != ids.size())
            throw new ResourceNotFoundException("One or more customer roles not found for the given ids");

        customerRoleRepository.deleteAll(customerRoles);
    }

}
