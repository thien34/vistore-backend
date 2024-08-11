package com.example.back_end.core.admin.product.payload.response;

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
public class ProductSpecificationAttributeMappingResponse {

    Long id;

    Long productId;

    Long specificationAttributeOptionId;

    String specificationAttributeOptionName;

    String customValue;

    boolean showOnProductPage;

    int displayOrder;

    String attributeType;

    Long specificationAttributeId;

    String specificationAttributeName;

}
