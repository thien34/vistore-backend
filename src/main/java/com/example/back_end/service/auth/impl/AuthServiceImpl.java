package com.example.back_end.service.auth.impl;

import com.example.back_end.core.admin.customer.mapper.CustomerMapper;
import com.example.back_end.core.admin.customer.payload.response.CustomerFullResponse;
import com.example.back_end.core.client.auth.payload.request.LoginRequest;
import com.example.back_end.core.client.auth.payload.response.LoginResponse;
import com.example.back_end.entity.Customer;
import com.example.back_end.entity.CustomerPassword;
import com.example.back_end.infrastructure.exception.NotFoundException;
import com.example.back_end.repository.CustomerPasswordRepository;
import com.example.back_end.repository.CustomerRepository;
import com.example.back_end.service.auth.AuthService;
import com.example.back_end.service.customer.CustomerPasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final CustomerRepository customerRepository;
    private final CustomerPasswordRepository passwordRepository;
    private final CustomerPasswordService passwordService;
    private final CustomerMapper customerMapper;

    //    todo: hãy handle ex cho auth
    //    todo: token nếu cần
    @Override
    public LoginResponse authenticate(LoginRequest loginRequest) {

        Customer customer = customerRepository.findByEmailAndDeletedFalse(loginRequest.getEmail()).orElseThrow(
                () -> new NotFoundException("Invalid email or password")
        );
        if (!customer.getActive()) {
            throw new NotFoundException("Account is not activated");
        }
        CustomerPassword customerPassword = passwordRepository.findByCustomer(customer)
                .orElseThrow(() -> new NotFoundException("Password not found"));
        if (!passwordService.verifyPassword(loginRequest.getRawPassword(), customerPassword)) {
            throw new NotFoundException("Invalid email or password");
        }

        CustomerFullResponse customerDTO = customerMapper.toFullResponse(customer);

        return new LoginResponse(customerDTO);
    }

}
