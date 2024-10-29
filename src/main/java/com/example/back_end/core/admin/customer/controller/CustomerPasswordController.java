package com.example.back_end.core.admin.customer.controller;

import com.example.back_end.core.admin.customer.payload.request.CustomerPasswordRequest;
import com.example.back_end.core.admin.customer.payload.response.CustomerPasswordResponse;
import com.example.back_end.core.common.PageResponse;
import com.example.back_end.core.common.ResponseData;
import com.example.back_end.service.customer.CustomerPasswordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/customer-passwords")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerPasswordController {

    CustomerPasswordService customerPasswordService;

    @PostMapping
    @Operation(summary = "Create a new customer password", description = "Create a new customer password with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Customer password created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request payload")
    })
    public ResponseData<CustomerPasswordResponse> createCustomerPassword(@RequestBody CustomerPasswordRequest request) {
        CustomerPasswordResponse response = customerPasswordService.createCustomerPassword(request);
        return ResponseData.<CustomerPasswordResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Customer password created successfully")
                .data(response)
                .build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update customer password by ID", description = "Update an existing customer password using the provided ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer password updated successfully"),
            @ApiResponse(responseCode = "404", description = "Customer password not found")
    })
    public ResponseData<Void> updateCustomerPassword(@PathVariable Long id, @RequestBody CustomerPasswordRequest request) {
        customerPasswordService.updateCustomerPassword(id, request);
        return ResponseData.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Customer password updated successfully")
                .data(null)
                .build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a customer password by ID", description = "Delete an existing customer password using the provided ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Customer password deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Customer password not found")
    })
    public ResponseData<Void> deleteCustomerPassword(@PathVariable Long id) {
        customerPasswordService.deleteCustomerPassword(id);
        return ResponseData.<Void>builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message("Customer password deleted successfully")
                .build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get customer password by ID", description = "Retrieve customer password details using the provided ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer password found successfully"),
            @ApiResponse(responseCode = "404", description = "Customer password not found")
    })
    public ResponseData<CustomerPasswordResponse> getCustomerPasswordById(@PathVariable Long id) {
        CustomerPasswordResponse response = customerPasswordService.getCustomerPasswordById(id);
        return ResponseData.<CustomerPasswordResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Customer password retrieved successfully")
                .data(response)
                .build();
    }

    @GetMapping
    @Operation(summary = "Get all customer passwords", description = "Retrieve all customer passwords with optional pagination.")
    public ResponseData<PageResponse<List<CustomerPasswordResponse>>> getAllCustomerPasswords(
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "6") Integer pageSize) {

        PageResponse<List<CustomerPasswordResponse>> response = customerPasswordService.getAll(pageNo, pageSize);

        return ResponseData.<PageResponse<List<CustomerPasswordResponse>>>builder()
                .status(HttpStatus.OK.value())
                .message("Get all customer passwords successfully")
                .data(response)
                .build();
    }

}
