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

    SPECIFICATION_ATTRIBUTE_GROUP_NOT_EXISTED(1008, "Specification attribute group does not exist", HttpStatus.OK),

    SPECIFICATION_ATTRIBUTE_GROUP_EXISTED(1009, "Specification attribute group existed", HttpStatus.OK),

    SPECIFICATION_ATTRIBUTE_NOT_EXISTED(1010, "Specification attribute does not exist", HttpStatus.OK),

    SPECIFICATION_ATTRIBUTE_EXISTED(1011, "Specification attribute existed", HttpStatus.OK),

    SPECIFICATION_ATTRIBUTE_OPTION_NOT_EXISTS(1012, "Specification attribute option does not exist", HttpStatus.OK),

    SPECIFICATION_ATTRIBUTE_OPTION_EXISTED(1013, "Specification attribute option existed", HttpStatus.OK),

    PRODUCT_ATTRIBUTE_ALREADY_EXISTS(1014, "Product attribute already exist,you can reuse it", HttpStatus.OK),

    PRODUCT_SPECIFICATION_ATTRIBUTE_MAPPING_NOT_FOUND(1014, "Product specification attribute mapping not found", HttpStatus.NOT_FOUND),

    PREDEFINED_PRODUCT_ATTRIBUTE_VALUE_NOT_EXISTED(1015, "Predefined product attribute value does not existed", HttpStatus.NOT_FOUND),

    FORBIDDEN(1016, "You do not have permission", HttpStatus.FORBIDDEN),

    INVALID_PAGE_NUMBER_OR_PAGE_SIZE(1017, "Invalid page number or page size", HttpStatus.BAD_REQUEST),

    PREDEFINED_PRODUCT_ATTRIBUTE_NAME_EXISTED(1018, "The predefined product attribute name already exists, you can reuse it", HttpStatus.BAD_REQUEST),

    PRODUCT_ATTRIBUTE_EXISTED(1019, "Product attribute already existed", HttpStatus.BAD_REQUEST),

    PRODUCT_SPECIFICATION_ATTRIBUTE_MAPPING_EXISTED(1020, "Product specification attribute mapping already existed", HttpStatus.BAD_REQUEST),

    PRODUCT_SPECIFICATION_ATTRIBUTE_MAPPING_NOT_EXISTED(1020, "Product specification attribute mapping does not exists", HttpStatus.BAD_REQUEST),

    ID_SPECIFICATION_ATTRIBUTE_OPTION_INVALID(1021,"Invalid specification attribute option ID",HttpStatus.BAD_REQUEST),
    ID_SPECIFICATION_ATTRIBUTE_INVALID(1021,"Invalid specification attribute ID",HttpStatus.BAD_REQUEST);


    int code;
    String message;
    HttpStatusCode statusCode;

}
