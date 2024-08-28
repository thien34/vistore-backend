package com.example.back_end.core.admin.category.payload.request;

import com.example.back_end.entity.Category;
import com.example.back_end.entity.Picture;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRequest {

    @NotBlank(message = "Name is required")
    @Size(max = 25, message = "Name must be max 25 characters")
    @Pattern(regexp = "^[^<>]*$", message = "Name must not contain special characters")
    private String name;

    @Pattern(regexp = "^(?!.*<script>).*$", message = "must not contain script tag")
    @Size(max = 255, message = "Description must be max 255 characters")
    private String description;

    private Long categoryParentId;

    private Long pictureId;

    private Boolean showOnHomePage;

    private Boolean includeInTopMenu;

    @NotNull(message = "Page size is required")
    @Digits(integer = 9, fraction = 0, message = "Display order must be a valid number with up to 9 digits")
    @Max(value = 214748647, message = "Page size must be max 214748647")
    @Min(value = 1, message = "Page size must be min 1")
    private Integer pageSize;

    private Boolean published;

    @NotNull(message = "Display order is required")
    @Digits(integer = 9, fraction = 0, message = "Display order must be a valid number with up to 9 digits")
    @Max(value = 214748647, message = "Display order must be max 214748647")
    @Min(value = 0, message = "Display order must be min 0")
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