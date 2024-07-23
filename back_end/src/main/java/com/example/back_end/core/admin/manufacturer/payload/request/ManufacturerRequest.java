package com.example.back_end.core.admin.manufacturer.payload.request;

import com.example.back_end.entity.Picture;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManufacturerRequest {

    @NotBlank(message = "Name cannot be blank")
    private String name;

    private String description;

    private Long pictureId;

    private Integer pageSize;

    private Boolean priceRangeFiltering;

    private Boolean published;

    private Integer displayOrder;

    public Picture getPicture() {
        if (pictureId == null) {
            return null;
        }
        Picture picture = new Picture();
        picture.setId(pictureId);
        return picture;
    }
}