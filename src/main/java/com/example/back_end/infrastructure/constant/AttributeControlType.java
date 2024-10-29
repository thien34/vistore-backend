package com.example.back_end.infrastructure.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum AttributeControlType {

    DROPDOWN("dropdown"),
    RADIO_BUTTON("radio_button"),
    COLOR_SQUARES("color_squares");

    private String label;
}
