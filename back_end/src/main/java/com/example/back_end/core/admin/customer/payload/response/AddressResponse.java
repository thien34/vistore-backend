package com.example.back_end.core.admin.customer.payload.response;

import com.example.back_end.infrastructure.constant.AddressType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressResponse {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String company;

    private String districtId;

    private String provinceId;

    private String wardId;

    private String addressName;

    private String phoneNumber;

    private Long customerId;

    private AddressType addressTypeId;

}
