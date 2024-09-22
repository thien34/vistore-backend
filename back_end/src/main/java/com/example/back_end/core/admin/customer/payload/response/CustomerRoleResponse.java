package com.example.back_end.core.admin.customer.payload.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerRoleResponse {

    Integer id;

    String name;

    Boolean active;

}
