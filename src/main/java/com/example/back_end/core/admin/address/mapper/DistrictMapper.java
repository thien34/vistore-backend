package com.example.back_end.core.admin.address.mapper;

import com.example.back_end.core.admin.address.payload.response.DistrictResponse;
import com.example.back_end.entity.District;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DistrictMapper {

    DistrictResponse toResponse(District district);

    List<DistrictResponse> toResponseList(List<District> districts);

}
