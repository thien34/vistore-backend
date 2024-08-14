package com.example.back_end.core.admin.product.mapper;

import com.example.back_end.core.admin.product.payload.request.ProductSpecificationAttributeMappingRequest;
import com.example.back_end.core.admin.product.payload.response.ProductSpecificationAttributeMappingResponse;
import com.example.back_end.entity.ProductSpecificationAttributeMapping;
import com.example.back_end.infrastructure.exception.CustomJsonProcessingException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductSpecificationAttributeMappingMapper {

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "specificationAttributeOption.id", target = "specificationAttributeOptionId")
    @Mapping(source = "specificationAttributeOption.name", target = "specificationAttributeOptionName")
    @Mapping(target = "specificationAttributeId", expression = "java(getSpecificationAttributeId(mapping))")
    @Mapping(target = "specificationAttributeName", expression = "java(getSpecificationAttributeName(mapping))")
    ProductSpecificationAttributeMappingResponse toDto(ProductSpecificationAttributeMapping mapping);

    List<ProductSpecificationAttributeMappingResponse> toDto(
            List<ProductSpecificationAttributeMapping> productSpecificationAttributeMappings);

    @Mapping(target = "product", ignore = true)
    @Mapping(target = "specificationAttributeOption", ignore = true)
    ProductSpecificationAttributeMapping toEntity(
            ProductSpecificationAttributeMappingRequest productSpecificationAttributeMappingRequest);

    default Long getSpecificationAttributeId(ProductSpecificationAttributeMapping mapping) {
        if (mapping.getSpecificationAttributeOption() != null) {
            return mapping.getSpecificationAttributeOption().getSpecificationAttribute().getId();
        } else {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode customValueNode = objectMapper.readTree(mapping.getCustomValue());
                return customValueNode.has("spec_attribute_id") ? customValueNode
                        .get("spec_attribute_id").asLong() : null;
            } catch (JsonProcessingException e) {
                throw new CustomJsonProcessingException("Failed to parse customValue JSON", e);
            }
        }
    }

    default String getSpecificationAttributeName(ProductSpecificationAttributeMapping mapping) {
        if (mapping.getSpecificationAttributeOption() != null) {
            return mapping.getSpecificationAttributeOption().getSpecificationAttribute().getName();
        } else {
            // Extract name from customValue if no option
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode customValueNode = objectMapper.readTree(mapping.getCustomValue());
                return customValueNode.has("spec_attribute_name") ?
                        customValueNode.get("spec_attribute_name").asText() :
                        null;
            } catch (JsonProcessingException e) {
                throw new CustomJsonProcessingException("Failed to parse customValue JSON", e);
            }
        }
    }
}
