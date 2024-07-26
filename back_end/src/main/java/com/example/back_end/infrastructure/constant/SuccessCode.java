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
    OPERATION_SUCCESSFUL(2000, "Operation completed successfully", HttpStatus.OK),
    PRODUCT_ATTRIBUTE_CREATED(2001, "Product attribute created successfully", HttpStatus.CREATED),
    PRODUCT_ATTRIBUTE_GET_ALL(2002, "Get all product attribute successfully", HttpStatus.OK),
    PRODUCT_ATTRIBUTE_UPDATED(2003, "Product attribute updated successfully", HttpStatus.OK),
    PRODUCT_ATTRIBUTE_DELETED(2004, "Product attribute deleted successfully", HttpStatus.NO_CONTENT),
    PRODUCT_ATTRIBUTE_SEARCH(2005, "Product attribute deleted successfully", HttpStatus.NO_CONTENT),
    GET_PRODUCT_ATTRIBUTE_BY_ID(2006, "Get product attribute search by name successfully ", HttpStatus.OK),
    PREDEFINED_PRODUCT_ATTRIBUTE_VALUE_CREATED(2007, "Predefined product attribute value created successfully", HttpStatus.CREATED),
    PREDEFINED_PRODUCT_ATTRIBUTE_VALUE_GET_ALL(2008, "Get all predefined product attribute value successfully", HttpStatus.OK),
    PREDEFINED_PRODUCT_ATTRIBUTE_VALUE_UPDATED(2009, "Predefined product attribute value updated successfully", HttpStatus.OK),
    PREDEFINED_PRODUCT_ATTRIBUTE_VALUE_DELETED(2010, "Predefined product attribute value deleted successfully", HttpStatus.NO_CONTENT),
    PRODUCT_SPECIFICATION_ATTRIBUTE_MAPPING_CREATED(2011, "Product specification attribute mapping created successfully", HttpStatus.CREATED),
    PRODUCT_SPECIFICATION_ATTRIBUTE_MAPPING_GET_ALL(2012, "Get all specification attribute mapping successfully", HttpStatus.OK),
    PRODUCT_SPECIFICATION_ATTRIBUTE_MAPPING_UPDATED(2013, "Product specification attribute mapping updated successfully", HttpStatus.OK),
    PRODUCT_SPECIFICATION_ATTRIBUTE_MAPPING_DELETED(2014, "Product specification attribute mapping deleted successfully", HttpStatus.NO_CONTENT),
    GET_PRODUCT_SPECIFICATION_ATTRIBUTE_MAPPING_BY_ID(2015, "Get product specification attribute mapping by id successfully ", HttpStatus.OK),
    SPECIFICATION_ATTRIBUTE_OPTION_CREATED(2016, "Specification attribute option value created successfully", HttpStatus.CREATED),
    SPECIFICATION_ATTRIBUTE_OPTION_GET_ALL(2017, "Get all specification attribute option successfully", HttpStatus.OK),
    SPECIFICATION_ATTRIBUTE_OPTION_UPDATED(2018, "Specification attribute option value updated successfully", HttpStatus.OK),
    SPECIFICATION_ATTRIBUTE_OPTION_DELETED(2019, "Specification attribute option value deleted successfully", HttpStatus.NO_CONTENT),
    GET_SPECIFICATION_ATTRIBUTE_OPTION_BY_ID(2020, "Get specification attribute option by id successfully ", HttpStatus.OK),
    SPECIFICATION_ATTRIBUTE_CREATED(2021, "Specification attribute created successfully", HttpStatus.CREATED),
    SPECIFICATION_ATTRIBUTE_GET_ALL(2022, "Get all specification attribute successfully", HttpStatus.OK),
    SPECIFICATION_ATTRIBUTE_UPDATED(2023, "Specification attribute updated successfully", HttpStatus.OK),
    SPECIFICATION_ATTRIBUTE_DELETED(2024, "Specification attribute deleted successfully", HttpStatus.NO_CONTENT),
    GET_SPECIFICATION_ATTRIBUTE_BY_ID(2025, "Get specification attribute by id successfully ", HttpStatus.OK),
    SPECIFICATION_ATTRIBUTE_GROUP_CREATED(2026, "Specification attribute group value created successfully", HttpStatus.CREATED),
    SPECIFICATION_ATTRIBUTE_GROUP_GET_ALL(2027, "Get all specification attribute group successfully", HttpStatus.OK),
    SPECIFICATION_ATTRIBUTE_GROUP_UPDATED(2028, "Specification attribute group value updated successfully", HttpStatus.OK),
    SPECIFICATION_ATTRIBUTE_GROUP_DELETED(2029, "Specification attribute group value deleted successfully", HttpStatus.NO_CONTENT),
    GET_SPECIFICATION_ATTRIBUTE_GROUP_BY_ID(2030, "Get specification attribute option by id successfully ", HttpStatus.OK),;
    int code;
    String message;
    HttpStatusCode statusCode;
}
