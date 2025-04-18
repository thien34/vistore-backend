package com.example.back_end.core.admin.relatedProducts.mapper;

import com.example.back_end.core.admin.relatedProducts.payload.request.RelatedProductRequest;
import com.example.back_end.core.admin.relatedProducts.payload.response.RelatedProductResponse;
import com.example.back_end.entity.RelatedProduct;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RelatedProductMapper {

    @Mapping(target = "nameProduct2", source = "product2.name")
    @Mapping(target = "product2Id", source = "product2.id")
    @Mapping(target = "product1Id", source = "product1.id")
    RelatedProductResponse mapToRelatedProductResponse(RelatedProduct relatedProduct);

    @Mapping(target = "product1.id", source = "product1Id")
    @Mapping(target = "product2.id", source = "product2Id")
    void updateRelatedProduct(@MappingTarget RelatedProduct relatedProduct, RelatedProductRequest request);

    @Mapping(target = "product1.id", source = "product1Id")
    @Mapping(target = "product2.id", source = "product2Id")
    RelatedProduct mapToRelatedProduct(RelatedProductRequest request);

    List<RelatedProductResponse> mapToListRelatedProductResponse(Page<RelatedProduct> relatedProductPage);

}
