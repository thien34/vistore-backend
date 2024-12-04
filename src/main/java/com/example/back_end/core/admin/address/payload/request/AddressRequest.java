package com.example.back_end.core.admin.address.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressRequest {

    @NotBlank(message = "Họ là bắt buộc")
    private String firstName;

    @NotBlank(message = "Tên là bắt buộc")
    private String lastName;

    private String email;

    private String company;

    @NotNull(message = "Mã quận/huyện là bắt buộc")
    private String districtId;

    @NotNull(message = "Mã tỉnh thành là bắt buộc")
    private String provinceId;

    @NotNull(message = "Mã phường/xã là bắt buộc")
    private String wardId;

    @NotBlank(message = "Tên địa chỉ là bắt buộc")
    private String addressName;

    @Pattern(
            regexp = "^(0\\d{9}|\\+84\\d{9}|84\\d{9})$",
            message = "Số điện thoại không hợp lệ. Vui lòng nhập số điện thoại Việt Nam hợp lệ."
    )
    private String phoneNumber;

    @NotNull(message = "Mã khách hàng là bắt buộc")
    private Long customerId;

}
