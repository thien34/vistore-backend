package com.example.back_end.core.admin.category.payload.request;

import com.example.back_end.entity.Category;
import com.example.back_end.entity.Picture;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRequest {

    @NotBlank
    private String name;

    private String description;

    private Long categoryParentId;

    private Long pictureId;

    private Boolean showOnHomePage;

    private Boolean includeInTopMenu;

    private Integer pageSize;

    private Boolean published;

    private Integer displayOrder;

    private Boolean priceRangeFiltering;

    public Category getCategoryParent() {
        if (categoryParentId == null) {
            return null;
        }
        Category categoryParent = new Category();
        categoryParent.setId(categoryParentId);
        return categoryParent;
    }

    public Picture getPicture() {
        if (pictureId == null) {
            return null;
        }
        Picture picture = new Picture();
        picture.setId(pictureId);
        return picture;
    }
}