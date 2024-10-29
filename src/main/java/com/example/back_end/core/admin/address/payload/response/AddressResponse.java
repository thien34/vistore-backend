package com.example.back_end.core.admin.address.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressResponse {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String districtId;

    private String provinceId;

    private String wardId;

    private String addressName;

    private String phoneNumber;

}
