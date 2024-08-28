package com.example.back_end.core.admin.manufacturer.payload.request;

import com.example.back_end.entity.Picture;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManufacturerRequest {

    @NotBlank(message = "Name cannot be blank")
    @Size(max = 255, message = "Name must be shorter than 256 characters")
    @Pattern(regexp = "^[A-Za-z0-9]*$",message = "Name cannot contain special character")
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