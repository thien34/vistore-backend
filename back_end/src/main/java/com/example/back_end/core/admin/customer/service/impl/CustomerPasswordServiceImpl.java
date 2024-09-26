package com.example.back_end.core.admin.customer.service.impl;

import com.example.back_end.core.admin.customer.mapper.CustomerPasswordMapper;
import com.example.back_end.core.admin.customer.payload.request.CustomerPasswordRequest;
import com.example.back_end.core.admin.customer.payload.response.CustomerPasswordResponse;
import com.example.back_end.core.admin.customer.service.CustomerPasswordService;
import com.example.back_end.core.common.PageResponse;
import com.example.back_end.entity.CustomerPassword;
import com.example.back_end.infrastructure.constant.ErrorCode;
import com.example.back_end.infrastructure.constant.SortType;
import com.example.back_end.infrastructure.exception.NotFoundException;
import com.example.back_end.infrastructure.utils.PageUtils;
import com.example.back_end.repository.CustomerPasswordRepository;
import com.example.back_end.repository.CustomerRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true )
public class CustomerPasswordServiceImpl implements CustomerPasswordService {

    CustomerPasswordRepository customerPasswordRepository;
    CustomerPasswordMapper customerPasswordMapper;
    PasswordEncoder passwordEncoder;
    CustomerRepository customerRepository;

    @Override
    @Transactional
    public CustomerPasswordResponse createCustomerPassword(CustomerPasswordRequest request) {

        validateCustomerExists(request.getCustomerId());

        CustomerPassword customerPassword = customerPasswordMapper.toEntity(request);
        String hashPassword = encodePassword(request.getPassword());
        customerPassword.setPassword(hashPassword);
        return customerPasswordMapper.toResponse(customerPasswordRepository.save(customerPassword));
    }

    public String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    @Override
    @Transactional
    public void updateCustomerPassword(Long id, CustomerPasswordRequest request) {

        validateCustomerExists(request.getCustomerId());

        CustomerPassword customerPassword = customerPasswordRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CUSTOMER_PASSWORD_NOT_FOUND.getMessage()));

        String hashPassword = encodePassword(request.getPassword());
        customerPassword.setPassword(hashPassword);
        customerPassword.setPasswordSalt(request.getPasswordSalt());

        customerPasswordRepository.save(customerPassword);

    }

    @Override
    @Transactional
    public void deleteCustomerPassword(Long id) {

        CustomerPassword customerPassword = customerPasswordRepository.findById(id)
                .orElseThrow(() ->new NotFoundException(ErrorCode.CUSTOMER_PASSWORD_NOT_FOUND.getMessage()));

        customerPasswordRepository.delete(customerPassword);

    }

    @Override
    public CustomerPasswordResponse getCustomerPasswordById(Long id) {

        CustomerPassword customerPassword = customerPasswordRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CUSTOMER_PASSWORD_NOT_FOUND.getMessage()));

        return customerPasswordMapper.toResponse(customerPassword);

    }

    @Override
    public PageResponse<List<CustomerPasswordResponse>> getAll(Integer pageNo, Integer pageSize) {

        Pageable pageable = PageUtils.createPageable(pageNo, pageSize, "id", SortType.DESC.getValue());

        Page<CustomerPassword> customerPasswordPage = customerPasswordRepository.findAll(pageable);

        List<CustomerPasswordResponse> customerPasswordResponses = customerPasswordMapper
                .toResponseList(customerPasswordPage.getContent());

        return PageResponse.<List<CustomerPasswordResponse>>builder()
                .page(customerPasswordPage.getNumber())
                .size(customerPasswordPage.getSize())
                .totalPage(customerPasswordPage.getTotalPages())
                .items(customerPasswordResponses)
                .build();

    }

    private void validateCustomerExists(Long customerId) {

        if (!customerRepository.existsById(customerId))
            throw new NotFoundException(ErrorCode.CUSTOMER_NOT_FOUND.getMessage());

    }

}
