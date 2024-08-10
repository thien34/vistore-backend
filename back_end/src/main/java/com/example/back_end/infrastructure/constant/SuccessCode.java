package com.example.back_end.infrastructure.constant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum SuccessCode {
    OPERATION_SUCCESSFUL("Operation completed successfully", HttpStatus.OK),

    PRODUCT_ATTRIBUTE_CREATED("Product attribute created successfully", HttpStatus.CREATED),
    PRODUCT_ATTRIBUTE_GET_ALL("Get all product attribute successfully", HttpStatus.OK),
    PRODUCT_ATTRIBUTE_UPDATED("Product attribute updated successfully", HttpStatus.OK),
    PRODUCT_ATTRIBUTE_DELETED("Product attribute deleted successfully", HttpStatus.NO_CONTENT),
    PRODUCT_ATTRIBUTE_SEARCH("Product attribute deleted successfully", HttpStatus.NO_CONTENT),

    GET_PRODUCT_ATTRIBUTE_BY_ID("Get product attribute search by name successfully ", HttpStatus.OK),

    PREDEFINED_PRODUCT_ATTRIBUTE_VALUE_CREATED("Predefined product attribute value created successfully", HttpStatus.CREATED),
    PREDEFINED_PRODUCT_ATTRIBUTE_VALUE_GET_ALL("Get all predefined product attribute value successfully", HttpStatus.OK),
    PREDEFINED_PRODUCT_ATTRIBUTE_VALUE_UPDATED("Predefined product attribute value updated successfully", HttpStatus.OK),
    PREDEFINED_PRODUCT_ATTRIBUTE_VALUE_DELETED("Predefined product attribute value deleted successfully", HttpStatus.NO_CONTENT),

    PRODUCT_SPECIFICATION_ATTRIBUTE_MAPPING_CREATED("Product specification attribute mapping created successfully", HttpStatus.CREATED),
    PRODUCT_SPECIFICATION_ATTRIBUTE_MAPPING_GET_ALL("Get all specification attribute mapping successfully", HttpStatus.OK),
    PRODUCT_SPECIFICATION_ATTRIBUTE_MAPPING_UPDATED("Product specification attribute mapping updated successfully", HttpStatus.OK),
    PRODUCT_SPECIFICATION_ATTRIBUTE_MAPPING_DELETED("Product specification attribute mapping deleted successfully", HttpStatus.NO_CONTENT),

    GET_PRODUCT_SPECIFICATION_ATTRIBUTE_MAPPING_BY_ID("Get product specification attribute mapping by id successfully ", HttpStatus.OK),

    SPECIFICATION_ATTRIBUTE_OPTION_CREATED("Specification attribute option value created successfully", HttpStatus.CREATED),
    SPECIFICATION_ATTRIBUTE_OPTION_GET_ALL("Get all specification attribute option successfully", HttpStatus.OK),
    SPECIFICATION_ATTRIBUTE_OPTION_UPDATED("Specification attribute option value updated successfully", HttpStatus.OK),
    SPECIFICATION_ATTRIBUTE_OPTION_DELETED("Specification attribute option value deleted successfully", HttpStatus.NO_CONTENT),

    GET_SPECIFICATION_ATTRIBUTE_OPTION_BY_ID("Get specification attribute option by id successfully ", HttpStatus.OK),

    SPECIFICATION_ATTRIBUTE_CREATED("Specification attribute created successfully", HttpStatus.CREATED),
    SPECIFICATION_ATTRIBUTE_GET_ALL("Get all specification attribute successfully", HttpStatus.OK),
    SPECIFICATION_ATTRIBUTE_UPDATED("Specification attribute updated successfully", HttpStatus.OK),
    SPECIFICATION_ATTRIBUTE_DELETED("Specification attribute deleted successfully", HttpStatus.NO_CONTENT),

    GET_SPECIFICATION_ATTRIBUTE_BY_ID("Get specification attribute by id successfully ", HttpStatus.OK),

    SPECIFICATION_ATTRIBUTE_GROUP_CREATED("Specification attribute group value created successfully", HttpStatus.CREATED),
    SPECIFICATION_ATTRIBUTE_GROUP_GET_ALL("Get all specification attribute group successfully", HttpStatus.OK),
    SPECIFICATION_ATTRIBUTE_GROUP_UPDATED("Specification attribute group value updated successfully", HttpStatus.OK),

    SPECIFICATION_ATTRIBUTE_GROUP_DELETED("Specification attribute group value deleted successfully", HttpStatus.NO_CONTENT),
    GET_SPECIFICATION_ATTRIBUTE_GROUP_BY_ID("Get specification attribute option by id successfully ", HttpStatus.OK),
    ;

    String message;
    HttpStatusCode statusCode;
}
