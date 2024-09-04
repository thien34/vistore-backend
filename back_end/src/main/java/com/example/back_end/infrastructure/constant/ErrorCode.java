package com.example.back_end.infrastructure.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
@FieldDefaults(makeFinal = true)
public enum ErrorCode {

    UNCATEGORIZED_EXCEPTION("Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),

    // User-related errors
    INVALID_USERNAME("Username must be at least 2 characters", HttpStatus.BAD_REQUEST),
    INVALID_KEY("Uncategorized error", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD("Password must be at least 6 characters", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED("User not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED("Unauthenticated", HttpStatus.UNAUTHORIZED),
    USER_EXISTED("User existed", HttpStatus.BAD_REQUEST),
    FORBIDDEN("You do not have permission", HttpStatus.FORBIDDEN),

    // Product-related errors
    PRODUCT_NOT_FOUND("Product not found", HttpStatus.NOT_FOUND),

    //Discounts errors
    DISCOUNT_NOT_FOUND("Discount not found", HttpStatus.NOT_FOUND),
    DISCOUNT_ALREADY_EXISTS("Discount already exists", HttpStatus.BAD_REQUEST),
    DISCOUNT_NOT_ACTIVE("Discount is not active", HttpStatus.FORBIDDEN),
    DISCOUNT_WITH_THIS_NAME_ALREADY_EXISTS("Discount with this name already exists", HttpStatus.BAD_REQUEST),

    // Product-attributes-related errors
    PRODUCT_ATTRIBUTE_NOT_EXISTED("Product attribute does not exist", HttpStatus.NOT_FOUND),
    PRODUCT_ATTRIBUTE_ALREADY_EXISTS("Product attribute already exists, you can reuse it", HttpStatus.BAD_REQUEST),
    PRODUCT_ATTRIBUTE_EXISTED("Product attribute already existed", HttpStatus.BAD_REQUEST),

    // Specification attribute errors
    SPECIFICATION_ATTRIBUTE_GROUP_NOT_EXISTED("Specification attribute group does not exist", HttpStatus.NOT_FOUND),
    SPECIFICATION_ATTRIBUTE_GROUP_EXISTED("Specification attribute group existed", HttpStatus.BAD_REQUEST),
    SPECIFICATION_ATTRIBUTE_NOT_EXISTED("Specification attribute does not exist", HttpStatus.NOT_FOUND),
    SPECIFICATION_ATTRIBUTE_EXISTED("Specification attribute existed", HttpStatus.BAD_REQUEST),
    SPECIFICATION_ATTRIBUTE_OPTION_NOT_EXISTS("Specification attribute option does not exist", HttpStatus.NOT_FOUND),
    SPECIFICATION_ATTRIBUTE_OPTION_EXISTED("Specification attribute option existed", HttpStatus.BAD_REQUEST),

    // Predefined product attribute errors
    PREDEFINED_PRODUCT_ATTRIBUTE_VALUE_NOT_EXISTED(
            "Predefined product attribute value does not exist",
            HttpStatus.NOT_FOUND
    ),
    PREDEFINED_PRODUCT_ATTRIBUTE_NAME_EXISTED(
            "The predefined product attribute name already exists, you can reuse it",
            HttpStatus.BAD_REQUEST
    ),

    // Pagination errors
    INVALID_PAGE_NUMBER_OR_PAGE_SIZE("Invalid page number or page size", HttpStatus.BAD_REQUEST),

    // Product specification attribute mapping errors
    PRODUCT_SPECIFICATION_ATTRIBUTE_MAPPING_NOT_FOUND(
            "Product specification attribute mapping not found",
            HttpStatus.NOT_FOUND
    ),
    PRODUCT_SPECIFICATION_ATTRIBUTE_MAPPING_EXISTED(
            "Product specification attribute mapping already existed",
            HttpStatus.BAD_REQUEST
    ),
    PRODUCT_SPECIFICATION_ATTRIBUTE_MAPPING_NOT_EXISTED(
            "Product specification attribute mapping does not exist",
            HttpStatus.NOT_FOUND
    ),
    //Product video mapping
    PRODUCT_VIDEO_MAPPING_NOT_FOUND("Product video mapping not found", HttpStatus.NOT_FOUND),
    PRODUCT_VIDEO_MAPPING_NOT_EXISTS("Product video mapping not exists", HttpStatus.BAD_REQUEST),

    // ID validation errors
    ID_SPECIFICATION_ATTRIBUTE_OPTION_INVALID(
            "Invalid specification attribute option ID",
            HttpStatus.BAD_REQUEST
    ),
    ID_SPECIFICATION_ATTRIBUTE_INVALID("Invalid specification attribute ID", HttpStatus.BAD_REQUEST);

    String message;
    HttpStatusCode statusCode;

}
