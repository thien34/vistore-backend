package com.example.back_end.core.admin.manufacturer.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Setter
@NoArgsConstructor
@Builder
public class ManufacturerResponse {
    private Long id;
    private String name;
    private String description;
    private String pictureId;
    private Integer pageSize;
    private Boolean priceRangeFiltering;
    private Boolean published;
    private Boolean deleted;
    private Integer displayOrder;
}