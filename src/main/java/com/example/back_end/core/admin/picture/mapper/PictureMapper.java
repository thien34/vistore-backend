package com.example.back_end.core.admin.picture.mapper;

import com.example.back_end.core.admin.picture.payload.response.PictureResponse;
import com.example.back_end.entity.Picture;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PictureMapper {

    PictureResponse toDto(Picture picture);

}
