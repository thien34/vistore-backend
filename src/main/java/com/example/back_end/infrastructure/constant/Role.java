package com.example.back_end.infrastructure.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum Role {

    ADMINISTRATOR("Administrator"),
    GUEST("Guest"),
    CUSTOMER("Customer"),
    EMPLOYEE("Employee");

    private String label;

}
