package com.example.back_end.core.admin.customer.controller;

import com.example.back_end.core.admin.customer.payload.request.AddressRequest;
import com.example.back_end.core.admin.customer.payload.response.AddressResponse;
import com.example.back_end.core.admin.customer.service.AddressService;
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
@RequestMapping("/admin/addresses")
public class AddressController {

    AddressService addressService;

    @PostMapping
    @Operation(summary = "Create a new address", description = "Create a new address with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Address created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request payload")
    })
    public ResponseData<Void> createAddress(
            @Valid @RequestBody AddressRequest addressRequest) {
        addressService.createAddress(addressRequest);
        return ResponseData.<Void>builder()
                .status(HttpStatus.CREATED.value())
                .message("Address created successfully")
                .data(null)
                .build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update address by ID", description = "Update an existing address using the provided ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Address updated successfully"),
            @ApiResponse(responseCode = "404", description = "Address not found")
    })
    public ResponseData<Void> updateAddress(
            @PathVariable Long id,
            @Valid @RequestBody AddressRequest addressRequest) {
        addressService.updateAddress(id, addressRequest);
        return ResponseData.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Address updated successfully")
                .data(null)
                .build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get address by ID", description = "Retrieve address details using the provided ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Address found successfully"),
            @ApiResponse(responseCode = "404", description = "Address not found")
    })
    public ResponseData<AddressResponse> getAddressById(@PathVariable Long id) {
        AddressResponse response = addressService.getAddressById(id);
        return ResponseData.<AddressResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Address retrieved successfully")
                .data(response)
                .build();
    }

    @GetMapping
    @Operation(summary = "Get all addresses", description = "Retrieve all addresses with optional filtering by customer ID.")
    public ResponseData<PageResponse<List<AddressResponse>>> getAllAddresses(
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "6") Integer pageSize,
            @RequestParam(required = false) Long customerId) {
        PageResponse<List<AddressResponse>> response = addressService.getAll(pageNo, pageSize, customerId);
        return ResponseData.<PageResponse<List<AddressResponse>>>builder()
                .status(HttpStatus.OK.value())
                .message("Get all addresses successfully")
                .data(response)
                .build();
    }

    @DeleteMapping
    @Operation(summary = "Delete addresses by IDs", description = "Delete existing addresses using the provided IDs.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Addresses deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Addresses not found")
    })
    public ResponseData<Void> deleteAddresses(@RequestBody List<Long> ids) {
        addressService.deleteAddresses(ids);
        return ResponseData.<Void>builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message("Addresses deleted successfully")
                .build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete addresses by ID", description = "Delete existing addresses using the provided ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Address deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Address not found")
    })
    public ResponseData<Void> deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
        return ResponseData.<Void>builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message("Address deleted successfully")
                .build();
    }

}