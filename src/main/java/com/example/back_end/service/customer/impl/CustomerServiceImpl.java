package com.example.back_end.service.customer.impl;

import com.example.back_end.core.admin.customer.mapper.CustomerMapper;
import com.example.back_end.core.admin.customer.payload.request.CustomerPasswordRequest;
import com.example.back_end.core.admin.customer.payload.request.CustomerRequest;
import com.example.back_end.core.admin.customer.payload.request.CustomerSearchRequest;
import com.example.back_end.core.admin.customer.payload.request.CustomerUpdateRequest;
import com.example.back_end.core.admin.customer.payload.response.CustomerFullResponse;
import com.example.back_end.core.admin.customer.payload.response.CustomerResponse;
import com.example.back_end.core.common.PageResponse1;
import com.example.back_end.entity.Customer;
import com.example.back_end.entity.CustomerRole;
import com.example.back_end.entity.CustomerRoleMapping;
import com.example.back_end.infrastructure.constant.ErrorCode;
import com.example.back_end.infrastructure.exception.AlreadyExistsException;
import com.example.back_end.infrastructure.exception.NotFoundException;
import com.example.back_end.infrastructure.utils.PageUtils;
import com.example.back_end.repository.CustomerCustomerRoleMappingRepository;
import com.example.back_end.repository.CustomerRepository;
import com.example.back_end.repository.CustomerRoleRepository;
import com.example.back_end.service.customer.CustomerPasswordService;
import com.example.back_end.service.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final CustomerCustomerRoleMappingRepository customerCustomerRoleMappingRepository;
    private final CustomerRoleRepository customerRoleRepository;
    private final CustomerPasswordService customerPasswordService;

    @Override
    public PageResponse1<List<CustomerResponse>> getAllCustomers(CustomerSearchRequest searchRequest) {

        Pageable pageable = PageUtils.createPageable(
                searchRequest.getPageNo(),
                searchRequest.getPageSize(),
                searchRequest.getSortBy(),
                searchRequest.getSortDir());
        Specification<Customer> specification = CustomerSpecification.filterCustomers(searchRequest);

        Page<Customer> customerPage = customerRepository.findAll(specification, pageable);
        List<CustomerResponse> customerResponses = customerMapper.toResponseList(customerPage.getContent());

        return PageResponse1.<List<CustomerResponse>>builder()
                .totalItems(customerPage.getTotalElements())
                .totalPages(customerPage.getTotalPages())
                .items(customerResponses)
                .build();
    }

    @Override
    public CustomerFullResponse getCustomerById(Long id) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CUSTOMER_NOT_FOUND.getMessage()));
        return customerMapper.toFullResponse(customer);
    }

    @Override
    @Transactional
    public void createCustomer(CustomerRequest request) {

        checkForDuplicateEmail(request.getEmail());

        Customer customer = customerMapper.toEntity(request);
        customer.setCustomerGuid(UUID.randomUUID());
        customer.setActive(request.getActive() != null);
        customer.setDeleted(false);

        Customer customerSaved = customerRepository.save(customer);

        List<CustomerRoleMapping> mappings = createRoleMappings(request.getCustomerRoles(), customer);
        customerCustomerRoleMappingRepository.saveAll(mappings);

        customerPasswordService.createCustomerPassword(new CustomerPasswordRequest(customerSaved, "123456"));

    }

    private List<CustomerRoleMapping> createRoleMappings(List<Long> roleIds, Customer customer) {
        return roleIds.stream()
                .map(roleId -> customerRoleRepository.findById(roleId)
                        .orElseThrow(() -> new NotFoundException(ErrorCode.CUSTOMER_ROLE_NOT_FOUND.getMessage())))
                .map(role -> {
                    CustomerRoleMapping mapping = new CustomerRoleMapping();
                    mapping.setCustomer(customer);
                    mapping.setCustomerRole(role);
                    return mapping;
                })
                .toList();
    }

    @Override
    @Transactional
    public void updateCustomer(Long id, CustomerRequest request) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CUSTOMER_NOT_FOUND.getMessage()));

        customerMapper.updateFromFullRequest(request, customer);
        customerRepository.save(customer);

        updateCustomerRoles(customer, request.getCustomerRoles());
    }

    @Override
    @Transactional
    public void updateProfileInfo(Long id, CustomerUpdateRequest request) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CUSTOMER_NOT_FOUND.getMessage()));

        customerMapper.updateProfileInfo(request, customer);
        customerRepository.save(customer);
    }

    private void updateCustomerRoles(Customer customer, List<Long> newRoleIds) {
        List<CustomerRoleMapping> existingMappings = customer.getCustomerRoles();

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
    }

    private void checkForDuplicateEmail(String email) {
        if (customerRepository.findByEmail(email).isPresent()) {
            throw new AlreadyExistsException(ErrorCode.EMAIL_ALREADY_EXISTS.getMessage());
        }
    }

    @Override
    @Transactional
    public void deleteCustomers(List<Long> ids) {

        List<CustomerRole> customerRoles = customerRoleRepository.findAllById(ids);
        customerRoleRepository.deleteAll(customerRoles);
    }

}
