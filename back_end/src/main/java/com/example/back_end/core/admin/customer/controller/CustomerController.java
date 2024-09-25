package com.example.back_end.core.admin.customer.controller;

import com.example.back_end.core.admin.customer.payload.request.CustomerFullRequest;
import com.example.back_end.core.admin.customer.payload.request.CustomerSearchCriteria;
import com.example.back_end.core.admin.customer.payload.response.CustomerFullResponse;
import com.example.back_end.core.admin.customer.payload.response.CustomerResponse;
import com.example.back_end.core.admin.customer.service.CustomerService;
import com.example.back_end.core.common.PageResponse;
import com.example.back_end.core.common.ResponseData;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/customers")
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class CustomerController {

    CustomerService customerService;

    @GetMapping
    public ResponseData<PageResponse<List<CustomerResponse>>> getAllCustomers(
            CustomerSearchCriteria searchCriteria,
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize) {

        PageResponse<List<CustomerResponse>> response = customerService.getAllCustomers(searchCriteria, pageNo, pageSize);

        return ResponseData.<PageResponse<List<CustomerResponse>>>builder()
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
    public ResponseData<Void> createCustomer(@RequestBody @Valid CustomerFullRequest request) {
        customerService.createCustomer(request);
        return ResponseData.<Void>builder()
                .status(HttpStatus.CREATED.value())
                .message("Customer created successfully")
                .build();
    }

    @PutMapping("/{id}")
    public ResponseData<Void> updateCustomer(@PathVariable Long id, @RequestBody @Valid CustomerFullRequest request) {
        customerService.updateCustomer(id, request);
        return ResponseData.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Customer updated successfully")
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseData<Void> deleteCustomers(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseData.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Customers deleted successfully")
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
