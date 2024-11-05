package com.example.back_end.core.admin.product.mapper;

import com.example.back_end.core.admin.product.payload.request.ProductVideoMappingRequest;
import com.example.back_end.core.admin.product.payload.response.ProductVideoMappingResponse;
import com.example.back_end.entity.ProductVideoMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductVideoMappingMapper {

    @Mapping(source = "videoUrl", target = "video.videoUrl")
    @Mapping(source = "displayOrder", target = "displayOrder")
    @Mapping(target = "product", ignore = true)
    ProductVideoMapping toEntity(ProductVideoMappingRequest dto);

    @Mapping(source = "video.videoUrl", target = "videoUrl")
    @Mapping(source = "displayOrder", target = "displayOrder")
    @Mapping(source = "product.id", target = "productId")
    ProductVideoMappingResponse toResponseDTO(ProductVideoMapping entity);

}
