package com.example.back_end.core.admin.address.mapper;

import com.example.back_end.core.admin.address.payload.request.AddressRequest;
import com.example.back_end.core.admin.address.payload.response.AddressResponse;
import com.example.back_end.core.admin.address.payload.response.AddressesResponse;
import com.example.back_end.entity.Address;
import com.example.back_end.entity.District;
import com.example.back_end.entity.Province;
import com.example.back_end.entity.Ward;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AddressMapper {

    @Mapping(target = "customer.id", source = "customerId")
    @Mapping(target = "ward.code", source = "wardId")
    @Mapping(target = "district.code", source = "districtId")
    @Mapping(target = "province.code", source = "provinceId")
    Address toEntity(AddressRequest request);

    @Mapping(target = "provinceId", source = "province.code")
    @Mapping(target = "districtId", source = "district.code")
    @Mapping(target = "wardId", source = "ward.code")
    AddressResponse toResponse(Address address);

    List<AddressResponse> toResponseList(List<Address> addresses);

    @Mapping(target = "province", source = "provinceId", qualifiedByName = "toProvince")
    @Mapping(target = "district", source = "districtId", qualifiedByName = "toDistrict")
    @Mapping(target = "ward", source = "wardId", qualifiedByName = "toWard")
    void updateAddressFromRequest(AddressRequest request, @MappingTarget Address address);

    AddressesResponse toResponses(Address address);

    @Named("toProvince")
    default Province toProvince(String provinceId) {
        if (provinceId == null) {
            return null;
        }
        Province province = new Province();
        province.setCode(provinceId);
        return province;
    }

    @Named("toDistrict")
    default District toDistrict(String districtId) {
        if (districtId == null) {
            return null;
        }
        District district = new District();
        district.setCode(districtId);
        return district;
    }

    @Named("toWard")
    default Ward toWard(String wardId) {
        if (wardId == null) {
            return null;
        }
        Ward ward = new Ward();
        ward.setCode(wardId);
        return ward;
    }

}
