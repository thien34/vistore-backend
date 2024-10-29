package com.example.back_end.core.admin.role.controller;

import com.example.back_end.core.admin.role.payload.request.CustomerRoleRequest;
import com.example.back_end.core.admin.role.payload.request.RoleSearchRequest;
import com.example.back_end.core.admin.role.payload.response.CustomerRoleResponse;
import com.example.back_end.core.admin.role.payload.response.RoleNameResponse;
import com.example.back_end.core.common.PageResponse1;
import com.example.back_end.core.common.ResponseData;
import com.example.back_end.service.role.CustomerRoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/admin/customer-roles")
public class CustomerRoleController {

    private final CustomerRoleService customerRoleService;

    @PostMapping
    public ResponseData<Void> createCustomerRole(
            @Valid @RequestBody CustomerRoleRequest customerRoleRequest) {

        customerRoleService.createCustomerRole(customerRoleRequest);
        return ResponseData.<Void>builder()
                .status(HttpStatus.CREATED.value())
                .message("Customer role created successfully")
                .build();
    }

    @PutMapping("/{id}")
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
    public ResponseData<List<RoleNameResponse>> getAllNameCustomerRoles() {

        List<RoleNameResponse> response = customerRoleService.getAllCustomerRoleName();

        return ResponseData.<List<RoleNameResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Get customer roles name success")
                .data(response)
                .build();
    }

    @GetMapping("/{id}")
    public ResponseData<CustomerRoleResponse> getCustomerRoleById(@PathVariable Long id) {

        CustomerRoleResponse response = customerRoleService.getCustomerRole(id);
        return ResponseData.<CustomerRoleResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Customer role retrieved successfully")
                .data(response)
                .build();
    }

    @GetMapping
    public ResponseData<PageResponse1<List<CustomerRoleResponse>>> getAllCustomerRoles(@ParameterObject RoleSearchRequest searchRequest) {

        PageResponse1<List<CustomerRoleResponse>> response = customerRoleService.getAll(searchRequest);

        return ResponseData.<PageResponse1<List<CustomerRoleResponse>>>builder()
                .status(HttpStatus.OK.value())
                .message("Get all customer roles successfully")
                .data(response)
                .build();
    }

    @DeleteMapping
    public ResponseData<Void> deleteCustomerRoles(@RequestBody List<Long> id) {
        customerRoleService.deleteCustomerRoles(id);
        return ResponseData.<Void>builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message("Customer roles deleted successfully")
                .build();
    }

}
