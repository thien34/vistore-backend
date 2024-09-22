package com.example.back_end.core.admin.customer.service.impl;

import com.example.back_end.core.admin.customer.mapper.CustomerMapper;
import com.example.back_end.core.admin.customer.payload.request.CustomerFullRequest;
import com.example.back_end.core.admin.customer.payload.request.CustomerSearchCriteria;
import com.example.back_end.core.admin.customer.payload.response.CustomerResponse;
import com.example.back_end.core.admin.customer.service.CustomerService;
import com.example.back_end.core.common.PageResponse;
import com.example.back_end.entity.Customer;
import com.example.back_end.entity.CustomerRole;
import com.example.back_end.entity.CustomerRoleMapping;
import com.example.back_end.infrastructure.constant.ErrorCode;
import com.example.back_end.infrastructure.constant.SortType;
import com.example.back_end.infrastructure.exception.NotFoundException;
import com.example.back_end.repository.CustomerCustomerRoleMappingRepository;
import com.example.back_end.repository.CustomerRepository;
import com.example.back_end.infrastructure.utils.PageUtils;
import com.example.back_end.repository.CustomerRoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class CustomerServiceImpl implements CustomerService {

    CustomerRepository customerRepository;
    CustomerMapper customerMapper;
    CustomerCustomerRoleMappingRepository customerCustomerRoleMappingRepository;
    CustomerRoleRepository customerRoleRepository;

    @Override
    public PageResponse<List<CustomerResponse>> getAllCustomers(
            CustomerSearchCriteria searchCriteria,
            Integer pageNo,
            Integer pageSize) {

        Pageable pageable = PageUtils.createPageable(pageNo, pageSize, "id", SortType.DESC.getValue());

        Specification<Customer> specification = CustomerSpecification.filterCustomers(searchCriteria);

        Page<Customer> customerPage = customerRepository.findAll(specification, pageable);

        List<CustomerResponse> customerResponses = customerPage.getContent().stream()
                .map(customerMapper::toResponse)
                .toList();

        return PageResponse.<List<CustomerResponse>>builder()
                .page(customerPage.getNumber())
                .size(customerPage.getSize())
                .totalPage(customerPage.getTotalPages())
                .items(customerResponses)
                .build();
    }

    @Override
    public CustomerResponse getCustomerById(Long id) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CUSTOMER_NOT_FOUND.getMessage()));

        return customerMapper.toResponse(customer);

    }

    @Override
    @Transactional
    public void createCustomer(CustomerFullRequest request) {

        Customer customer = customerMapper.toEntity(request);
        customer.setCustomerGuid(UUID.randomUUID());

        customerRepository.save(customer);

        List<CustomerRole> roles = request.getCustomerRoles().stream()
                .map(roleId -> customerRoleRepository.findById(roleId)
                        .orElseThrow(() -> new NotFoundException(ErrorCode.CUSTOMER_ROLE_NOT_FOUND.getMessage())))
                .toList();

        List<CustomerRoleMapping> mappings = roles.stream()
                .map(role -> {
                    CustomerRoleMapping mapping = new CustomerRoleMapping();
                    mapping.setCustomer(customer);
                    mapping.setCustomerRole(role);
                    return mapping;
                })
                .toList();

        customerCustomerRoleMappingRepository.saveAll(mappings);
    }

    @Override
    @Transactional
    public void updateCustomer(Long id, CustomerFullRequest request) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CUSTOMER_NOT_FOUND.getMessage()));

        customerMapper.updateFromFullRequest(request, customer);

        List<CustomerRoleMapping> existingMappings = customer.getCustomerRoles();
        List<Long> newRoleIds = request.getCustomerRoles();

        existingMappings.removeIf(mapping -> !newRoleIds.contains(mapping.getCustomerRole().getId()));

        for (Long roleId : newRoleIds) {
            boolean exists = existingMappings.stream()
                    .anyMatch(mapping -> mapping.getCustomerRole().getId().equals(roleId));

            if (!exists) {
                CustomerRoleMapping newMapping = CustomerRoleMapping.builder()
                        .customer(customer)
                        .customerRole(new CustomerRole(roleId))
                        .build();
                existingMappings.add(newMapping);
            }
        }

        customerRepository.save(customer);

    }



    @Override
    @Transactional
    public void deleteCustomer(Long id) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CUSTOMER_NOT_FOUND.getMessage()));

        customerCustomerRoleMappingRepository.deleteAll(customer.getCustomerRoles());

        customerRepository.delete(customer);

    }

}
