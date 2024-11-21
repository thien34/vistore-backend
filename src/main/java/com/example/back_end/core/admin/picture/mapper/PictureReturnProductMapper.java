package com.example.back_end.core.admin.picture.mapper;

import com.example.back_end.core.admin.picture.payload.response.PictureReturnProductResponse;
import com.example.back_end.entity.PictureReturnProduct;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PictureReturnProductMapper {

    @Mapping(source = "id", target = "returnItemId")
    PictureReturnProductResponse toDto(PictureReturnProduct picture);

    @Mapping(source = "id", target = "returnItemId")
    List<PictureReturnProductResponse> toDtos(List<PictureReturnProduct> picture);

}
