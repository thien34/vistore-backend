package com.example.back_end.core.admin.product.payload.response;

import com.example.back_end.entity.ProductAttribute;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
public class ProductAttributeResponse {
    private Long id;
    private String name;
    private String description;
    private List<PredefinedProductAttributeValueResponse> values;
    public static ProductAttributeResponse mapToResponse(ProductAttribute productAttribute) {
        ProductAttributeResponse response = new ProductAttributeResponse();
        response.setId(productAttribute.getId());
        response.setName(productAttribute.getName());
        response.setDescription(productAttribute.getDescription());

        List<PredefinedProductAttributeValueResponse> values = productAttribute.getValues().stream()
                .map(PredefinedProductAttributeValueResponse::mapToResponse)
                .toList();
        response.setValues(values);

        return response;
    }
}
