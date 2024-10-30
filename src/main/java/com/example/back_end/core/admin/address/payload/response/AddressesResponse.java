package com.example.back_end.core.admin.address.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressesResponse {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String company;

    private String addressDetail;

    private String phoneNumber;

}
