package com.example.back_end.infrastructure.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum GenderType {

    MALE(0),
    FEMALE(1),
    OTHER(2);

    private Integer value;
}
