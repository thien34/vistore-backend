package com.example.back_end.core.admin.customer.mapper;

import com.example.back_end.core.admin.customer.payload.request.AddressRequest;
import com.example.back_end.core.admin.customer.payload.response.AddressResponse;
import com.example.back_end.entity.Address;
import com.example.back_end.entity.CustomerAddressMapping;
import com.example.back_end.infrastructure.constant.AddressType;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AddressMapper {

    Address toEntity(AddressRequest request);

    @Mapping(target = "customerId", ignore = true)
    @Mapping(target = "addressTypeId", expression = "java(getAddressType(address))")
    AddressResponse toResponse(Address address);

    List<AddressResponse> toResponseList(List<Address> addresses);

    void updateAddressFromRequest(AddressRequest request, @MappingTarget Address address);

    @AfterMapping
    default void mapCustomerId(Address address, @MappingTarget AddressResponse response) {
        if (address.getCustomerAddressMappings() != null && !address.getCustomerAddressMappings().isEmpty()) {
            response.setCustomerId(address.getCustomerAddressMappings().get(0).getCustomer().getId());
        }
    }

    default AddressType getAddressType(Address address) {
        if (address.getCustomerAddressMappings() != null && !address.getCustomerAddressMappings().isEmpty()) {
            CustomerAddressMapping mapping = address.getCustomerAddressMappings().get(0);
            return mapping.getAddressTypeId();
        }
        return null;
    }
}
