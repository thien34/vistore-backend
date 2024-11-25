package com.example.back_end.core.admin.address.controller;

import com.example.back_end.core.admin.address.payload.request.AddressRequest;
import com.example.back_end.core.admin.address.payload.request.AddressSearchRequest;
import com.example.back_end.core.admin.address.payload.response.AddressResponse;
import com.example.back_end.core.admin.address.payload.response.AddressesResponse;
import com.example.back_end.core.common.PageResponse1;
import com.example.back_end.core.common.ResponseData;
import com.example.back_end.service.address.AddressService;
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
@RequestMapping(value = "/admin/addresses")
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    public ResponseData<Void> createAddress(@Valid @RequestBody AddressRequest addressRequest) {

        addressService.createAddress(addressRequest);

        return ResponseData.<Void>builder()
                .status(HttpStatus.CREATED.value())
                .message("Address created successfully")
                .build();
    }

    @PutMapping("/{id}")
    public ResponseData<Void> updateAddress(@PathVariable Long id, @Valid @RequestBody AddressRequest addressRequest) {

        addressService.updateAddress(id, addressRequest);

        return ResponseData.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Address updated successfully")
                .build();
    }

    @GetMapping("/{id}")
    public ResponseData<AddressResponse> getAddressById(@PathVariable Long id) {

        AddressResponse response = addressService.getAddressById(id);

        return ResponseData.<AddressResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Address retrieved successfully")
                .data(response)
                .build();
    }

    @GetMapping
    public ResponseData<PageResponse1<List<AddressesResponse>>> getAllAddresses(
            @ParameterObject AddressSearchRequest searchRequest) {

        PageResponse1<List<AddressesResponse>> response = addressService.getAllAddressById(searchRequest);

        return ResponseData.<PageResponse1<List<AddressesResponse>>>builder()
                .message("Get all addresses successfully")
                .data(response)
                .build();
    }

    @DeleteMapping
    public ResponseData<Void> deleteAddresses(@RequestBody List<Long> ids) {

        addressService.deleteAddresses(ids);

        return ResponseData.<Void>builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message("Addresses deleted successfully")
                .build();
    }

}