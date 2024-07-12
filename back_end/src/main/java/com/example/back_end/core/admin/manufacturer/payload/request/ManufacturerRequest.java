package com.example.back_end.core.admin.manufacturer.payload.request;

import com.example.back_end.entity.Picture;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class ManufacturerRequest {
    @NotBlank(message = "Không được để trống tên")
    private String name;
    @NotBlank(message = "Không được để trống mô tả")
    private String description;
    @NotNull(message = "Vui lòng chọn ảnh")
    private Long pictureId;
    @NotNull(message = "Không được để trống pageSize")
    private Integer pageSize;
    @NotNull(message = "Không được để trống priceRangeFiltering")
    private Boolean priceRangeFiltering;
    @NotNull(message = "Không được để trống published")
    private Boolean published;
    @NotNull(message = "Không được để trống displayOrder")
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