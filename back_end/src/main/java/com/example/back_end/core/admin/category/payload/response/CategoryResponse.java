package com.example.back_end.core.admin.category.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryResponse {

    private Long id;

    private String name;

    private String description;

    private Long categoryParentId;

    private Long pictureId;

    private Boolean showOnHomePage;

    private Boolean includeInTopMenu;

    private Integer pageSize;

    private Boolean published;

    private Boolean deleted;

    private Integer displayOrder;

    private Boolean priceRangeFiltering;

}