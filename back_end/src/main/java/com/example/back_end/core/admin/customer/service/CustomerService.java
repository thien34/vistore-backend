package com.example.back_end.core.admin.customer.service;

import com.example.back_end.core.admin.customer.payload.request.CustomerFullRequest;
import com.example.back_end.core.admin.customer.payload.request.CustomerSearchCriteria;
import com.example.back_end.core.admin.customer.payload.response.CustomerFullResponse;
import com.example.back_end.core.admin.customer.payload.response.CustomerResponse;
import com.example.back_end.core.common.PageResponse;

import java.util.List;

public interface CustomerService {

    PageResponse<List<CustomerResponse>> getAllCustomers(
            CustomerSearchCriteria searchCriteria, Integer pageNo, Integer pageSize
    );

    CustomerFullResponse getCustomerById(Long id);

    void createCustomer(CustomerFullRequest request);

    void updateCustomer(Long id, CustomerFullRequest request);

    void deleteCustomer(Long id);

    void deleteCustomers(List<Long> ids);

}
