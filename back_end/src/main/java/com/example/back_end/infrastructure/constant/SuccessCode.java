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
    PRODUCT_ATTRIBUTE_GET_ALL(2002, "Get all product attribute successfully", HttpStatus.CREATED),
    PRODUCT_ATTRIBUTE_UPDATED(2003, "Product attribute updated successfully", HttpStatus.OK),
    PRODUCT_ATTRIBUTE_DELETED(2004, "Product attribute deleted successfully", HttpStatus.OK),
    GET_PRODUCT_ATTRIBUTE_BY_ID(2005, "Get product attribute successfully with id: ", HttpStatus.OK),
    PREDEFINED_PRODUCT_ATTRIBUTE_VALUE_CREATED(2006, "Predefined product attribute value created successfully", HttpStatus.OK),
    PREDEFINED_PRODUCT_ATTRIBUTE_VALUE_GET_ALL(2007, "Get all predefined product attribute value successfully", HttpStatus.OK),
    PREDEFINED_PRODUCT_ATTRIBUTE_VALUE_UPDATED(2008, "Predefined product attribute value updated successfully", HttpStatus.OK),
    PREDEFINED_PRODUCT_ATTRIBUTE_VALUE_DELETED(2009, "Predefined product attribute value deleted successfully", HttpStatus.OK);
    int code;
    String message;
    HttpStatusCode statusCode;
}
