package com.example.back_end.core.admin.manufacturer.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class ManufacturerNameResponse {
    private Long id;

    private String manufacturerName;
}
