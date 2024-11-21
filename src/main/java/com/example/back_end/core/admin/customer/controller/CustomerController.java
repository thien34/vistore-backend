package com.example.back_end.core.admin.customer.controller;

import com.example.back_end.core.admin.customer.payload.request.CustomerRequest;
import com.example.back_end.core.admin.customer.payload.request.CustomerSearchRequest;
import com.example.back_end.core.admin.customer.payload.response.CustomerFullResponse;
import com.example.back_end.core.admin.customer.payload.response.CustomerResponse;
import com.example.back_end.core.common.PageResponse1;
import com.example.back_end.core.common.ResponseData;
import com.example.back_end.service.customer.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(
        value = "/admin/customers",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public ResponseData<PageResponse1<List<CustomerResponse>>> getAllCustomers(
            @ParameterObject CustomerSearchRequest searchCriteria) {

        PageResponse1<List<CustomerResponse>> response = customerService.getAllCustomers(searchCriteria);

        return ResponseData.<PageResponse1<List<CustomerResponse>>>builder()
                .status(HttpStatus.OK.value())
                .message("Get customers success")
                .data(response)
                .build();
    }

    @GetMapping("/{id}")
    public ResponseData<CustomerFullResponse> getCustomerById(@PathVariable Long id) {

        CustomerFullResponse response = customerService.getCustomerById(id);

        return ResponseData.<CustomerFullResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Get customer success")
                .data(response)
                .build();
    }

    @PostMapping
    public ResponseData<Void> createCustomer(@RequestBody @Valid CustomerRequest request) {

        customerService.createCustomer(request);

        return ResponseData.<Void>builder()
                .status(HttpStatus.CREATED.value())
                .message("Customer created successfully")
                .build();
    }

    @PutMapping("/{id}")
    public ResponseData<Void> updateCustomer(@PathVariable Long id, @RequestBody @Valid CustomerRequest request) {

        customerService.updateCustomer(id, request);

        return ResponseData.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Customer updated successfully")
                .build();
    }

    @DeleteMapping
    public ResponseData<Void> deleteCustomers(@RequestBody List<Long> id) {

        customerService.deleteCustomers(id);

        return ResponseData.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Customers deleted successfully")
                .build();
    }

}
