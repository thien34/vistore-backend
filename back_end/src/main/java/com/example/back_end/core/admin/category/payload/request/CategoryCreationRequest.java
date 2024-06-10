package com.example.back_end.core.admin.category.payload.request;

import com.example.back_end.entity.Category;
import com.example.back_end.entity.Picture;
import lombok.Getter;

@Getter
public class CategoryCreationRequest {

    private String name;

    private String description;

    private Category categoryParent;

    private Picture picture;

    private Boolean showOnHomePage;

    private Boolean includeInTopMenu;

    private Integer pageSize;

    private Boolean published;

    private Integer displayOrder;

    private Boolean priceRangeFiltering;

}
