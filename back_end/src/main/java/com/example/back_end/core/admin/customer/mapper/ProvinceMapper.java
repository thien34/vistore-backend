package com.example.back_end.core.admin.customer.mapper;

import com.example.back_end.core.admin.customer.payload.response.ProvinceResponse;
import com.example.back_end.entity.Province;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProvinceMapper {

    ProvinceResponse toResponse(Province province);

    List<ProvinceResponse> toResponseList(List<Province> provinces);

}
