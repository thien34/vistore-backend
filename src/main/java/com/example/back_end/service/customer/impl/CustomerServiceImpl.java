package com.example.back_end.service.customer.impl;

import com.example.back_end.core.admin.customer.mapper.CustomerMapper;
import com.example.back_end.core.admin.customer.payload.request.CustomerRequest;
import com.example.back_end.core.admin.customer.payload.request.CustomerSearchRequest;
import com.example.back_end.core.admin.customer.payload.response.CustomerFullResponse;
import com.example.back_end.core.admin.customer.payload.response.CustomerResponse;
import com.example.back_end.core.common.PageResponse1;
import com.example.back_end.entity.Customer;
import com.example.back_end.entity.CustomerPassword;
import com.example.back_end.entity.CustomerRole;
import com.example.back_end.entity.CustomerRoleMapping;
import com.example.back_end.infrastructure.constant.ErrorCode;
import com.example.back_end.infrastructure.exception.AlreadyExistsException;
import com.example.back_end.infrastructure.exception.NotFoundException;
import com.example.back_end.infrastructure.utils.PageUtils;
import com.example.back_end.repository.CustomerCustomerRoleMappingRepository;
import com.example.back_end.repository.CustomerPasswordRepository;
import com.example.back_end.repository.CustomerRepository;
import com.example.back_end.repository.CustomerRoleRepository;
import com.example.back_end.service.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCrypt;
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
    private final CustomerPasswordRepository customerPasswordRepository;

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
        customer.setActive(false);
        customer.setDeleted(false);

        customerRepository.save(customer);

        List<CustomerRoleMapping> mappings = createRoleMappings(request.getCustomerRoles(), customer);
        customerCustomerRoleMappingRepository.saveAll(mappings);

//        CustomerPassword customerPassword = createCustomerPassword(customer, request.getPassword());
//        customerPasswordRepository.save(customerPassword);
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

    private CustomerPassword createCustomerPassword(Customer customer, String password) {
        String[] passData = hashPasswordAndGenerateSalt(password);
        return CustomerPassword.builder()
                .customer(customer)
                .password(passData[0])
                .passwordSalt(passData[1])
                .build();
    }

    private String[] hashPasswordAndGenerateSalt(String password) {
        String salt = BCrypt.gensalt(12);
        String hashedPassword = BCrypt.hashpw(password, salt);
        return new String[]{hashedPassword, salt};
    }

    @Override
    @Transactional
    public void updateCustomer(Long id, CustomerRequest request) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CUSTOMER_NOT_FOUND.getMessage()));

        customerMapper.updateFromFullRequest(request, customer);
        customerRepository.save(customer);

        updateCustomerRoles(customer, request.getCustomerRoles());

//        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
//            updateCustomerPassword(customer, request.getPassword());
//        }
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

    private void checkForDuplicateEmailAndIdNot(String email, Long id) {
        if (customerRepository.existsByEmailAndIdNot(email, id)) {
            throw new AlreadyExistsException(ErrorCode.EMAIL_ALREADY_EXISTS.getMessage());
        }
    }

    private void updateCustomerPassword(Customer customer, String newPassword) {
        String[] passwordData = hashPasswordAndGenerateSalt(newPassword);
        CustomerPassword customerPassword = customerPasswordRepository.findByCustomer(customer)
                .orElseGet(CustomerPassword::new);
        customerPassword.setCustomer(customer);
        customerPassword.setPassword(passwordData[0]);
        customerPassword.setPasswordSalt(passwordData[1]);
        customerPasswordRepository.save(customerPassword);
    }

    @Override
    @Transactional
    public void deleteCustomers(List<Long> ids) {
        List<CustomerRole> customerRoles = customerRoleRepository.findAllById(ids);

        customerRoleRepository.deleteAll(customerRoles);
    }

}
