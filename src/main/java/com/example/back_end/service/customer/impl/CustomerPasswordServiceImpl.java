package com.example.back_end.service.customer.impl;

import com.example.back_end.core.admin.customer.payload.request.CustomerPasswordRequest;
import com.example.back_end.entity.CustomerPassword;
import com.example.back_end.repository.CustomerPasswordRepository;
import com.example.back_end.service.customer.CustomerPasswordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class CustomerPasswordServiceImpl implements CustomerPasswordService {

    private final CustomerPasswordRepository customerPasswordRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void createCustomerPassword(CustomerPasswordRequest request) {

        String hashedPassword = passwordEncoder.encode(request.getRawPassword());
        CustomerPassword customerPassword = CustomerPassword.builder()
                .customer(request.getCustomer())
                .password(hashedPassword)
                .build();

        customerPasswordRepository.save(customerPassword);
    }

    @Override
    public boolean verifyPassword(String rawPassword, CustomerPassword customerPassword) {
        if (rawPassword == null || customerPassword == null || customerPassword.getPassword() == null) {
            return false;
        }
        try {
            return passwordEncoder.matches(rawPassword, customerPassword.getPassword());
        } catch (Exception e) {
            // Log error nhưng không throw để tránh leak thông tin
            log.error("Lỗi xác minh mật khẩu", e);
            return false;
        }
    }

}
