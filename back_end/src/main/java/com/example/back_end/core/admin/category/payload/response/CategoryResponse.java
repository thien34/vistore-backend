package com.example.back_end.core.admin.category.payload.response;

import com.example.back_end.entity.Category;
import com.example.back_end.entity.Picture;
import lombok.Getter;

@Getter
public class CategoryResponse {

    private Long id;

    private String name;

    private String description;

    private Category categoryParent;

    private Picture picture;

    private Boolean showOnHomePage;

    private Boolean includeInTopMenu;

    private Integer pageSize;

    private Boolean published;

    private Boolean deleted;

    private Integer displayOrder;

    private Boolean priceRangeFiltering;

}