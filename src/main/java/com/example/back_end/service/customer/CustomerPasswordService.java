package com.example.back_end.service.customer;

import com.example.back_end.core.admin.customer.payload.request.CustomerPasswordRequest;
import com.example.back_end.core.admin.customer.payload.response.CustomerPasswordResponse;
import com.example.back_end.core.common.PageResponse;

import java.util.List;

public interface CustomerPasswordService {

    CustomerPasswordResponse createCustomerPassword(CustomerPasswordRequest request);

    void updateCustomerPassword(Long id, CustomerPasswordRequest request);

    void deleteCustomerPassword(Long id);

    CustomerPasswordResponse getCustomerPasswordById(Long id);

    PageResponse<List<CustomerPasswordResponse>> getAll(Integer pageNo, Integer pageSize);

}
