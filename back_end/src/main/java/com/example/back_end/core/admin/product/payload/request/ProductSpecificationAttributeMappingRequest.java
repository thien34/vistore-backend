package com.example.back_end.core.admin.product.payload.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductSpecificationAttributeMappingRequest {

    Long productId;

    Long specificationAttributeOptionId;

    String customValue;

    boolean showOnProductPage;

    Integer displayOrder;

    Long specificationAttributeId;

}
