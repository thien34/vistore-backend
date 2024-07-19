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
public enum ErrorCode {

    UNCATEGORIZED_EXCEPTION(9999, "Uncategozied error", HttpStatus.INTERNAL_SERVER_ERROR),

    INVALID_USERNAME(1001, "Username must be at least 2 characters", HttpStatus.BAD_REQUEST),

    INVALID_KEY(1002, "Uncategozied error", HttpStatus.BAD_REQUEST),

    INVALID_PASSWORD(1003, "Password must be at least 6 characters", HttpStatus.BAD_REQUEST),

    USER_NOT_EXISTED(1004, "User not existed", HttpStatus.NOT_FOUND),

    UNAUTHENTICATED(1005, "Unauthenticated", HttpStatus.UNAUTHORIZED),

    USER_EXISTED(1006, "User existed", HttpStatus.BAD_REQUEST),

    PRODUCT_ATTRIBUTE_NOT_EXISTED(1007, "Product attribute does not exist", HttpStatus.OK),

    PRODUCT_ATTRIBUTE_ALREADY_EXISTS(1007, "Product attribute already exist,you can reuse it", HttpStatus.OK),

    PREDEFINED_PRODUCT_ATTRIBUTE_VALUE_NOT_EXISTED(1008, "Predefined product attribute value does not existed", HttpStatus.NOT_FOUND),

    FORBIDDEN(1009, "You do not have permission", HttpStatus.FORBIDDEN),

    INVALID_PAGE_NUMBER_OR_PAGE_SIZE(1010, "Invalid page number or page size", HttpStatus.BAD_REQUEST),

    PREDEFINED_PRODUCT_ATTRIBUTE_NAME_EXISTED(1011, "The predefined product attribute name already exists, you can reuse it", HttpStatus.BAD_REQUEST),

    PRODUCT_ATTRIBUTE_EXISTED(1012, "Product attribute already existed", HttpStatus.BAD_REQUEST);

    int code;
    String message;
    HttpStatusCode statusCode;

}
