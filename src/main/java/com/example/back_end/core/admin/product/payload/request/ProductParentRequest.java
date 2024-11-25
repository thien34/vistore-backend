package com.example.back_end.core.admin.product.payload.request;

import com.example.back_end.entity.Category;
import com.example.back_end.entity.Manufacturer;
import com.example.back_end.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductParentRequest {
    private String name;
    private Long categoryId;
    private Long manufacturerId;
    private BigDecimal weight;
    private String description;

    public void toEntity(ProductParentRequest productParentRequest, Product product) {
        product.setName(productParentRequest.getName());
        product.setCategory(new Category(productParentRequest.getCategoryId()));
        product.setManufacturer(new Manufacturer(productParentRequest.getManufacturerId()));
        product.setWeight(productParentRequest.getWeight());
        product.setFullDescription(productParentRequest.getDescription());
    }
}
