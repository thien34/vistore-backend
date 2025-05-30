package com.example.back_end.service.address;

import com.example.back_end.core.admin.address.payload.request.AddressRequest;
import com.example.back_end.core.admin.address.payload.request.AddressSearchRequest;
import com.example.back_end.core.admin.address.payload.response.AddressResponse;
import com.example.back_end.core.admin.address.payload.response.AddressesResponse;
import com.example.back_end.core.common.PageResponse1;

import java.util.List;

public interface AddressService {

    PageResponse1<List<AddressesResponse>> getAllAddressById(AddressSearchRequest searchRequest);

    AddressResponse getAddressById(Long id);

    void createAddress(AddressRequest request);

    void updateAddress(Long id, AddressRequest request);

    void deleteAddresses(List<Long> ids);

}
