package com.example.back_end.service.customer;

import com.example.back_end.core.admin.customer.payload.request.CustomerRequest;
import com.example.back_end.core.admin.customer.payload.request.CustomerSearchRequest;
import com.example.back_end.core.admin.customer.payload.response.CustomerFullResponse;
import com.example.back_end.core.admin.customer.payload.response.CustomerResponse;
import com.example.back_end.core.common.PageResponse1;

import java.util.List;

public interface CustomerService {

    void createCustomer(CustomerRequest request);

    void updateCustomer(Long id, CustomerRequest request);

    PageResponse1<List<CustomerResponse>> getAllCustomers(CustomerSearchRequest searchRequest);

    CustomerFullResponse getCustomerById(Long id);

    void deleteCustomers(List<Long> ids);

}
