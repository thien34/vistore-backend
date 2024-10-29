package com.example.back_end.core.admin.address.mapper;

import com.example.back_end.core.admin.address.payload.request.AddressRequest;
import com.example.back_end.core.admin.address.payload.response.AddressResponse;
import com.example.back_end.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AddressMapper {

    Address toEntity(AddressRequest request);

    AddressResponse toResponse(Address address);

    List<AddressResponse> toResponseList(List<Address> addresses);

    void updateAddressFromRequest(AddressRequest request, @MappingTarget Address address);

}
