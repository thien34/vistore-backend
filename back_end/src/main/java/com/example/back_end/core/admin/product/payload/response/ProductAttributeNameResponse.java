package com.example.back_end.core.admin.product.payload.response;

import com.example.back_end.entity.ProductAttribute;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductAttributeNameResponse {
    private String name;
    private String description;
    private List<PredefinedProductAttributeValueResponse> values;
    public static ProductAttributeNameResponse mapToResponse(ProductAttribute attribute) {
        ProductAttributeNameResponse response = new ProductAttributeNameResponse();
        response.setName(attribute.getName());
        response.setDescription(attribute.getDescription());
        response.setValues(attribute.getValues().stream()
                .map(PredefinedProductAttributeValueResponse::mapToResponse)
                .toList());
        return response;
    }
}
