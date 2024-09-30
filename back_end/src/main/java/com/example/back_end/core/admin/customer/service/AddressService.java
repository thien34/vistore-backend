package com.example.back_end.core.admin.customer.service;

import com.example.back_end.core.admin.customer.payload.request.AddressRequest;
import com.example.back_end.core.admin.customer.payload.response.AddressResponse;
import com.example.back_end.core.common.PageResponse;

import java.util.List;

public interface AddressService {

    PageResponse<List<AddressResponse>> getAll(Integer pageNo, Integer pageSize, Long customerId);

    void createAddress(AddressRequest request);

    void updateAddress(Long id, AddressRequest request);

    void deleteAddresses(List<Long> ids);

    AddressResponse getAddressById(Long id);

    void deleteAddress(Long id);

}
