package com.example.back_end.core.admin.product.payload.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductSpecificationAttributeMappingByProductResponse {

    Long id;

    String attributeType;

    String specificationAttributeName;

    String specificationAttributeOptionName;

    String customValue;

    boolean showOnProductPage;

    int displayOrder;

}