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
    PREDEFINED_PRODUCT_ATTRIBUTE_VALUE_DELETED(2010, "Predefined product attribute value deleted successfully", HttpStatus.NO_CONTENT);
    int code;
    String message;
    HttpStatusCode statusCode;
}
