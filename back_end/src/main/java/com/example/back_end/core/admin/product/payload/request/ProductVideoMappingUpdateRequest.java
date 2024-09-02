package com.example.back_end.core.admin.product.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductVideoMappingUpdateRequest {

    private Long id;

    private String videoUrl;

    private Integer displayOrder;

    private Long productId;

    private boolean isUpload;

}
