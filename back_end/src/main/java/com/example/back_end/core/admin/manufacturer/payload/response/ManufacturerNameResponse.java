package com.example.back_end.core.admin.manufacturer.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ManufacturerNameResponse {
    private Long id;

    private String manufacturerName;
}
