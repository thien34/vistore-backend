package com.example.back_end.core.admin.product.mapper;

import com.example.back_end.core.admin.product.payload.request.ProductSpecificationAttributeMappingRequest;
import com.example.back_end.core.admin.product.payload.response.ProductSpecificationAttributeMappingByIdResponse;
import com.example.back_end.core.admin.product.payload.response.ProductSpecificationAttributeMappingByProductResponse;
import com.example.back_end.core.admin.product.payload.response.ProductSpecificationAttributeMappingResponse;
import com.example.back_end.entity.ProductSpecificationAttributeMapping;
import com.example.back_end.entity.SpecificationAttributeOption;
import com.example.back_end.infrastructure.exception.CustomJsonProcessingException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

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

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "specificationAttributeOption.name", target = "specificationAttributeOptionName")
    @Mapping(target = "specificationAttributeName", expression = "java(getSpecificationAttributeName(mapping))")
    ProductSpecificationAttributeMappingByIdResponse toDtooo(ProductSpecificationAttributeMapping mapping);

    @Mapping(source = "specificationAttributeOption.name", target = "specificationAttributeOptionName")
    @Mapping(target = "specificationAttributeName", expression = "java(getSpecificationAttributeName(mapping))")
    @Mapping(target = "customValue", expression = "java(getCustomValue(mapping))")
    ProductSpecificationAttributeMappingByProductResponse toDtos(ProductSpecificationAttributeMapping mapping);

    @Mapping(target = "product.id", source = "productId")
    @Mapping(target = "specificationAttributeOption", source = "specificationAttributeOptionId", qualifiedByName = "mapSpecificationAttributeOption")
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
            // if the option exists, return the attribute name
            return mapping.getSpecificationAttributeOption().getSpecificationAttribute().getName();
        } else {
            // if customValue is JSON or plain text
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode customValueNode = objectMapper.readTree(mapping.getCustomValue());

                String idSpecAtb = customValueNode.has("spec_attribute_id") ? customValueNode
                        .get("spec_attribute_id").asText() : null;
                return idSpecAtb;
            } catch (JsonProcessingException e) {
                throw new CustomJsonProcessingException("Failed to parse customValue JSON", e);
            }
        }
    }

    default String getCustomValue(ProductSpecificationAttributeMapping mapping) {
        if (mapping.getSpecificationAttributeOption() == null) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode customValueNode = objectMapper.readTree(mapping.getCustomValue());

                String customValue = customValueNode.has("custom_value") ? customValueNode
                        .get("custom_value").asText() : null;
                return customValue;
            } catch (JsonProcessingException e) {
                throw new CustomJsonProcessingException("Failed to parse customValue JSON", e);
            }
        }
        return mapping.getCustomValue();
    }

    @Named("mapSpecificationAttributeOption")
    default SpecificationAttributeOption mapSpecificationAttributeOption(Long specificationAttributeOptionId) {
        if (specificationAttributeOptionId == null) {
            return null;
        }
        SpecificationAttributeOption option = new SpecificationAttributeOption();
        option.setId(specificationAttributeOptionId);
        return option;
    }

}