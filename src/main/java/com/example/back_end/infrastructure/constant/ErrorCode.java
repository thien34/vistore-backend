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

    UNCATEGORIZED_EXCEPTION("Lỗi chưa phân loại", HttpStatus.INTERNAL_SERVER_ERROR),

    // Lỗi liên quan đến người dùng
    INVALID_USERNAME("Tên người dùng phải có ít nhất 2 ký tự", HttpStatus.BAD_REQUEST),
    INVALID_KEY("Lỗi chưa phân loại", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD("Mật khẩu phải có ít nhất 6 ký tự", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED("Người dùng không tồn tại", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED("Chưa xác thực", HttpStatus.UNAUTHORIZED),
    USER_EXISTED("Người dùng đã tồn tại", HttpStatus.BAD_REQUEST),
    FORBIDDEN("Bạn không có quyền truy cập", HttpStatus.FORBIDDEN),

    // Lỗi liên quan đến sản phẩm
    PRODUCT_NOT_FOUND("Sản phẩm không tìm thấy", HttpStatus.NOT_FOUND),

    // Lỗi liên quan đến khách hàng
    CUSTOMER_NOT_EXISTED("Khách hàng không tồn tại", HttpStatus.NOT_FOUND),
    EMAIL_ALREADY_EXISTS("Email đã tồn tại.", HttpStatus.BAD_REQUEST),
    CUSTOMER_ROLE_NOT_FOUND("Vai trò khách hàng không tìm thấy", HttpStatus.NOT_FOUND),
    CUSTOMER_NOT_FOUND("Khách hàng không tìm thấy", HttpStatus.NOT_FOUND),
    CUSTOMER_PASSWORD_NOT_FOUND("Mật khẩu khách hàng không tìm thấy", HttpStatus.NOT_FOUND),
    PHONE_NUMBER_ALREADY_EXISTS("Số điện thoại đã tồn tại", HttpStatus.BAD_REQUEST),

    // Lỗi hệ thống
    SYSTEM_ROLE_COULD_NOT_BE_DELETED("Vai trò hệ thống không thể bị xóa", HttpStatus.BAD_REQUEST),

    // Lỗi liên quan đến địa chỉ
    ADDRESS_NOT_FOUND("Địa chỉ không tìm thấy", HttpStatus.NOT_FOUND),

    // Lỗi liên quan đến giảm giá
    DISCOUNT_NOT_FOUND("Giảm giá không tìm thấy", HttpStatus.NOT_FOUND),
    DISCOUNT_ALREADY_EXISTS("Giảm giá đã tồn tại", HttpStatus.BAD_REQUEST),
    DISCOUNT_NOT_ACTIVE("Giảm giá không hoạt động", HttpStatus.FORBIDDEN),
// DISCOUNT_WITH_THIS_NAME_ALREADY_EXISTS("Giảm giá với tên này đã tồn tại", HttpStatus.BAD_REQUEST),

    // Lỗi liên quan đến thuộc tính sản phẩm
    PRODUCT_ATTRIBUTE_NOT_EXISTED("Thuộc tính sản phẩm không tồn tại", HttpStatus.NOT_FOUND),
    PRODUCT_ATTRIBUTE_ALREADY_EXISTS("Thuộc tính sản phẩm đã tồn tại, bạn có thể tái sử dụng nó", HttpStatus.BAD_REQUEST),
    PRODUCT_ATTRIBUTE_EXISTED("Thuộc tính sản phẩm đã tồn tại", HttpStatus.BAD_REQUEST),

    // Lỗi liên quan đến thuộc tính đặc tả
    SPECIFICATION_ATTRIBUTE_GROUP_NOT_EXISTED("Nhóm thuộc tính đặc tả không tồn tại", HttpStatus.NOT_FOUND),
    SPECIFICATION_ATTRIBUTE_GROUP_EXISTED("Nhóm thuộc tính đặc tả đã tồn tại", HttpStatus.BAD_REQUEST),
    SPECIFICATION_ATTRIBUTE_NOT_EXISTED("Thuộc tính đặc tả không tồn tại", HttpStatus.NOT_FOUND),
    SPECIFICATION_ATTRIBUTE_EXISTED("Thuộc tính đặc tả đã tồn tại", HttpStatus.BAD_REQUEST),
    SPECIFICATION_ATTRIBUTE_OPTION_NOT_EXISTS("Tùy chọn thuộc tính đặc tả không tồn tại", HttpStatus.NOT_FOUND),
    SPECIFICATION_ATTRIBUTE_OPTION_EXISTED("Tùy chọn thuộc tính đặc tả đã tồn tại", HttpStatus.BAD_REQUEST),

    // Lỗi phân trang
    INVALID_PAGE_NUMBER_OR_PAGE_SIZE("Số trang hoặc kích thước trang không hợp lệ", HttpStatus.BAD_REQUEST),

    // Lỗi liên quan đến ánh xạ thuộc tính đặc tả của sản phẩm
    PRODUCT_SPECIFICATION_ATTRIBUTE_MAPPING_NOT_FOUND(
            "Không tìm thấy ánh xạ thuộc tính đặc tả sản phẩm",
            HttpStatus.NOT_FOUND
    ),
    PRODUCT_SPECIFICATION_ATTRIBUTE_MAPPING_EXISTED(
            "Ánh xạ thuộc tính đặc tả sản phẩm đã tồn tại",
            HttpStatus.BAD_REQUEST
    ),
    PRODUCT_SPECIFICATION_ATTRIBUTE_MAPPING_NOT_EXISTED(
            "Ánh xạ thuộc tính đặc tả sản phẩm không tồn tại",
            HttpStatus.NOT_FOUND
    ),

    // Lỗi xác thực ID
    ID_SPECIFICATION_ATTRIBUTE_OPTION_INVALID(
            "ID tùy chọn thuộc tính đặc tả không hợp lệ",
            HttpStatus.BAD_REQUEST
    ),
    ID_SPECIFICATION_ATTRIBUTE_INVALID("ID thuộc tính đặc tả không hợp lệ", HttpStatus.BAD_REQUEST);

    String message;
    HttpStatusCode statusCode;

}
