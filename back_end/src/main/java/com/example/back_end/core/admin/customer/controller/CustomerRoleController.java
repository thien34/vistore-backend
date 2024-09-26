package com.example.back_end.core.admin.customer.controller;

import com.example.back_end.core.admin.customer.payload.request.CustomerRoleRequest;
import com.example.back_end.core.admin.customer.payload.response.CustomerRoleResponse;
import com.example.back_end.core.admin.customer.service.CustomerRoleService;
import com.example.back_end.core.common.PageResponse;
import com.example.back_end.core.common.ResponseData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/admin/customer-roles")
public class CustomerRoleController {

    CustomerRoleService customerRoleService;

    @PostMapping
    @Operation(summary = "Create a new customer role",
            description = "Create a new customer role with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Customer role created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request payload")
    })
    public ResponseData<CustomerRoleResponse> createCustomerRole(
            @Valid
            @RequestBody CustomerRoleRequest customerRoleRequest
    ) {
        CustomerRoleResponse response = customerRoleService.createCustomerRole(customerRoleRequest);
        return ResponseData.<CustomerRoleResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Customer role created successfully")
                .data(response)
                .build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update customer role by ID",
            description = "Update an existing customer role using the provided ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer role updated successfully"),
            @ApiResponse(responseCode = "404", description = "Customer role not found")
    })
    public ResponseData<Void> updateCustomerRole(
            @PathVariable Long id,
            @Valid @RequestBody CustomerRoleRequest customerRoleRequest) {

        customerRoleService.updateCustomerRole(id, customerRoleRequest);
        return ResponseData.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Customer role updated successfully")
                .data(null)
                .build();
    }
    @GetMapping("/list-name")
    public ResponseData<List<CustomerRoleResponse>> getAllNameCustomerRoles() {

        List<CustomerRoleResponse> response = customerRoleService.getAllCustomerRoleName();

        return ResponseData.<List<CustomerRoleResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Get customer roles name success")
                .data(response)
                .build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get customer role by ID",
            description = "Retrieve customer role details using the provided ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer role found successfully"),
            @ApiResponse(responseCode = "404", description = "Customer role not found")
    })
    public ResponseData<CustomerRoleResponse> getCustomerRoleById(@PathVariable Long id) {
        CustomerRoleResponse response = customerRoleService.getCustomerRole(id);
        return ResponseData.<CustomerRoleResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Customer role retrieved successfully")
                .data(response)
                .build();
    }

    @GetMapping
    @Operation(summary = "Get all customer roles", description = "Retrieve all customer roles with optional filtering.")
    public ResponseData<PageResponse<List<CustomerRoleResponse>>> getAllCustomerRoles(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Boolean active,
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "6") Integer pageSize) {

        PageResponse<List<CustomerRoleResponse>> response = customerRoleService.getAll(name, active, pageNo, pageSize);
        return ResponseData.<PageResponse<List<CustomerRoleResponse>>>builder()
                .status(HttpStatus.OK.value())
                .message("Get all customer roles successfully")
                .data(response)
                .build();
    }

    @DeleteMapping
    @Operation(summary = "Delete a customer role by IDs",
            description = "Delete an existing customer role using the provided IDs.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Customer roles deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Customer roles not found")
    })
    public ResponseData<Void> deleteCustomerRoles(@RequestBody List<Long> id) {
        customerRoleService.deleteCustomerRoles(id);
        return ResponseData.<Void>builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message("Customer roles deleted successfully")
                .build();
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a customer role by ID",
            description = "Delete an existing customer role using the provided ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Customer role deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Customer role not found")
    })
    public ResponseData<Void> deleteCustomerRole(@PathVariable Long id) {
        customerRoleService.deleteCustomerRole(id);
        return ResponseData.<Void>builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message("Customer role deleted successfully")
                .build();
    }

}
