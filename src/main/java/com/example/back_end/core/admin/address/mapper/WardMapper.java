package com.example.back_end.core.admin.address.mapper;

import com.example.back_end.core.admin.address.payload.response.WardResponse;
import com.example.back_end.entity.Ward;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WardMapper {

    WardResponse toResponse(Ward ward);

    List<WardResponse> toResponseList(List<Ward> wards);

}
