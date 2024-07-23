package com.example.back_end.core.admin.product.mapper;

import com.example.back_end.core.admin.product.payload.request.ProductAttributeRequest;
import com.example.back_end.core.admin.product.payload.response.ProductAttributeResponse;
import com.example.back_end.entity.ProductAttribute;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring", uses = PredefinedProductAttributeValueMapper.class)
public interface ProductAttributeMapper {
    @Mapping(target = "id", ignore = true)
    ProductAttribute toEntity(ProductAttributeRequest dto);
    @Mapping(target = "values", source = "values")
    ProductAttributeResponse toDto(ProductAttribute entity);

    List<ProductAttribute> toEntities(List<ProductAttributeRequest> dtos);

    List<ProductAttributeResponse> toDtos(List<ProductAttribute> entities);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "values", ignore = true)
    void updateEntityFromRequest(ProductAttributeRequest request, @MappingTarget ProductAttribute entity);

    default List<ProductAttributeResponse> toDtos(Page<ProductAttribute> page) {
        return toDtos(page.getContent());
    }
}
