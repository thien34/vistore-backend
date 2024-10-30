package com.example.back_end.core.admin.address.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressRequest {

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    private String email;

    private String company;

    @NotNull(message = "District ID is required")
    private String districtId;

    @NotNull(message = "Province ID is required")
    private String provinceId;

    @NotNull(message = "Ward ID is required")
    private String wardId;

    @NotBlank(message = "Address name is required")
    private String addressName;

    @Pattern(
            regexp = "^(0\\d{9}|\\+84\\d{9}|84\\d{9})$",
            message = "Số điện thoại không hợp lệ. Vui lòng nhập số điện thoại Việt Nam hợp lệ."
    )
    private String phoneNumber;

    @NotNull(message = "Customer ID is required")
    private Long customerId;

}
