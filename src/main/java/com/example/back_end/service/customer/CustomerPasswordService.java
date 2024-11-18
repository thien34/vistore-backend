package com.example.back_end.service.customer;

import com.example.back_end.core.admin.customer.payload.request.CustomerPasswordRequest;
import com.example.back_end.entity.CustomerPassword;

public interface CustomerPasswordService {

    void createCustomerPassword(CustomerPasswordRequest request);

    boolean verifyPassword(String hashedPassword, CustomerPassword customerPassword);

}
