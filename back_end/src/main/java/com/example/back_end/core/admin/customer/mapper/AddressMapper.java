package com.example.back_end.core.admin.customer.mapper;

import com.example.back_end.core.admin.customer.payload.request.AddressRequest;
import com.example.back_end.core.admin.customer.payload.response.AddressResponse;
import com.example.back_end.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    Address toEntity(AddressRequest request);

    AddressResponse toResponse(Address address);

    List<AddressResponse> toResponseList(List<Address> addresses);

    void updateAddressFromRequest(AddressRequest request, @MappingTarget Address address);

}
