package com.example.back_end.core.admin.product.mapper;

import com.example.back_end.core.admin.product.payload.request.ProductSpecificationAttributeMappingRequest;
import com.example.back_end.core.admin.product.payload.response.ProductSpecificationAttributeMappingByProductResponse;
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
    @Mapping(source = "specificationAttributeOption.name", target = "specificationAttributeOptionName")
    @Mapping(target = "specificationAttributeName", expression = "java(getSpecificationAttributeName(mapping))")
    ProductSpecificationAttributeMappingByProductResponse toDtos(ProductSpecificationAttributeMapping mapping);

    List<ProductSpecificationAttributeMappingByProductResponse> toDtos(
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
            // No option, check if customValue is JSON or plain text
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode customValueNode = objectMapper.readTree(mapping.getCustomValue());

                // Extract custom_value from JSON if it exists
                if (customValueNode.isObject() && customValueNode.has("custom_value")) {
                    return customValueNode.get("custom_value").asText();
                } else {
                    // Not JSON or no custom_value, return the raw customValue
                    return mapping.getCustomValue();
                }
            } catch (JsonProcessingException e) {
                return mapping.getCustomValue();
            }
        }
    }

}
