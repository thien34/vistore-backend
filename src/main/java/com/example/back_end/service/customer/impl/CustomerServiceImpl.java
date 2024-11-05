package com.example.back_end.service.customer.impl;

import com.example.back_end.core.admin.customer.mapper.CustomerMapper;
import com.example.back_end.core.admin.customer.payload.request.CustomerFullRequest;
import com.example.back_end.core.admin.customer.payload.request.CustomerSearchCriteria;
import com.example.back_end.core.admin.customer.payload.response.CustomerFullResponse;
import com.example.back_end.core.admin.customer.payload.response.CustomerResponse;
import com.example.back_end.service.customer.CustomerService;
import com.example.back_end.core.common.PageResponse;
import com.example.back_end.entity.Customer;
import com.example.back_end.entity.CustomerPassword;
import com.example.back_end.entity.CustomerRole;
import com.example.back_end.entity.CustomerRoleMapping;
import com.example.back_end.infrastructure.constant.ErrorCode;
import com.example.back_end.infrastructure.constant.SortType;
import com.example.back_end.infrastructure.exception.AlreadyExistsException;
import com.example.back_end.infrastructure.exception.NotFoundException;
import com.example.back_end.infrastructure.utils.PageUtils;
import com.example.back_end.repository.CustomerCustomerRoleMappingRepository;
import com.example.back_end.repository.CustomerPasswordRepository;
import com.example.back_end.repository.CustomerRepository;
import com.example.back_end.repository.CustomerRoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
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
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerServiceImpl implements CustomerService {

    CustomerRepository customerRepository;
    CustomerMapper customerMapper;
    CustomerCustomerRoleMappingRepository customerCustomerRoleMappingRepository;
    CustomerRoleRepository customerRoleRepository;
    CustomerPasswordRepository customerPasswordRepository;

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
    public CustomerFullResponse getCustomerById(Long id) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CUSTOMER_NOT_FOUND.getMessage()));

        return customerMapper.toFullResponse(customer);
    }

    @Override
    @Transactional
    public void createCustomer(CustomerFullRequest request) {
        checkForDuplicateEmail(request.getEmail());
        checkForDuplicatePhoneNumber(request.getPhone());

        Customer customer = customerMapper.toEntity(request);
        customer.setCustomerGuid(UUID.randomUUID());

        customerRepository.save(customer);

        List<CustomerRoleMapping> mappings = createRoleMappings(request.getCustomerRoles(), customer);
        customerCustomerRoleMappingRepository.saveAll(mappings);

        CustomerPassword customerPassword = createCustomerPassword(customer, request.getPassword());
        customerPasswordRepository.save(customerPassword);
    }

    private void checkForDuplicatePhoneNumber(String phoneNumber) {
        if (customerRepository.findByPhone(phoneNumber).isPresent()) {
            throw new AlreadyExistsException(ErrorCode.PHONE_NUMBER_ALREADY_EXISTS.getMessage());
        }
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
    public void updateCustomer(Long id, CustomerFullRequest request) {
        checkForDuplicateEmailAndIdNot(request.getEmail(), id);
        checkForDuplicatePhoneNumberAndIdNot(request.getPhone(), id);

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CUSTOMER_NOT_FOUND.getMessage()));

        customerMapper.updateFromFullRequest(request, customer);
        updateCustomerRoles(customer, request.getCustomerRoles());

        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            updateCustomerPassword(customer, request.getPassword());
        }

        customerRepository.save(customer);
    }

    private void checkForDuplicatePhoneNumberAndIdNot(String phoneNumber, Long id) {
        if (customerRepository.existsByPhoneAndIdNot(phoneNumber, id)) {
            throw new AlreadyExistsException(ErrorCode.PHONE_NUMBER_ALREADY_EXISTS.getMessage());
        }
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
    public void deleteCustomer(Long id) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CUSTOMER_NOT_FOUND.getMessage()));

        customerCustomerRoleMappingRepository.deleteAll(customer.getCustomerRoles());

        customerRepository.delete(customer);
    }

    @Override
    @Transactional
    public void deleteCustomers(List<Long> ids) {
        List<CustomerRole> customerRoles = customerRoleRepository.findAllById(ids);

        customerRoleRepository.deleteAll(customerRoles);
    }

}
