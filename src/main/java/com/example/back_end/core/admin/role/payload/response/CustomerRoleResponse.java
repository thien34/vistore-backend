package com.example.back_end.core.admin.role.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerRoleResponse {

    private Long id;

    private String name;

    private Boolean active;

}
