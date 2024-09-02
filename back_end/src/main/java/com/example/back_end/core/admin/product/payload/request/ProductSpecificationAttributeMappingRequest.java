package com.example.back_end.core.admin.product.payload.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductSpecificationAttributeMappingRequest {

    Long productId;

    Long specificationAttributeOptionId;

    String customValue;

    boolean showOnProductPage;

    Integer displayOrder;

}
