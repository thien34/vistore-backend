package com.example.back_end.core.admin.manufacturer.payload.request;

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
    private Long id;
    @NotBlank(message = "Không được để trống tên")
    private String name;
    @NotBlank(message = "Không được để trống mô tả")
    private String description;
    private String img;
    @NotNull(message = "Không được để trống pageSize")
    private Integer pageSize;
    @NotNull(message = "Không được để trống priceRangeFiltering")
    private Boolean priceRangeFiltering;
    @NotNull(message = "Không được để trống published")
    private Boolean published;
    @NotNull(message = "Không được để trống deleted")
    private Boolean deleted;
    @NotNull(message = "Không được để trống displayOrder")
    private Integer displayOrder;
}