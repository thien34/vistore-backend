package com.example.back_end.core.admin.address.mapper;

import com.example.back_end.core.admin.address.payload.request.AddressRequest;
import com.example.back_end.core.admin.address.payload.response.AddressResponse;
import com.example.back_end.core.admin.address.payload.response.AddressesResponse;
import com.example.back_end.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AddressMapper {

    @Mapping(target = "customer.id", source = "customerId")
    @Mapping(target = "ward.code", source = "wardId")
    @Mapping(target = "district.code", source = "districtId")
    @Mapping(target = "province.code", source = "provinceId")
    Address toEntity(AddressRequest request);

    AddressResponse toResponse(Address address);

    List<AddressResponse> toResponseList(List<Address> addresses);

    void updateAddressFromRequest(AddressRequest request, @MappingTarget Address address);

    AddressesResponse toResponses(Address address);

}
