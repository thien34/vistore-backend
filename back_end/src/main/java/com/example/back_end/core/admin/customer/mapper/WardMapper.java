package com.example.back_end.core.admin.customer.mapper;

import com.example.back_end.core.admin.customer.payload.response.WardResponse;
import com.example.back_end.entity.Ward;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WardMapper {

    @Mapping(target = "districtCode", source = "districtCode.code")
    WardResponse toResponse(Ward ward);

    List<WardResponse> toResponseList(List<Ward> wards);

}
