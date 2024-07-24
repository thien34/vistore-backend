package com.example.back_end.core.admin.product.payload.response;

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

}
