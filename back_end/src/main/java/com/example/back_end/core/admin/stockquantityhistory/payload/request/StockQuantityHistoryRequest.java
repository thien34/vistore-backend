package com.example.back_end.core.admin.stockquantityhistory.payload.request;

import com.example.back_end.entity.Product;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockQuantityHistoryRequest {

    @NotNull(message = "Product invalid")
    private Long productId;

    @NotNull(message = " Quantity Adjustment  invalid")
    private Integer quantityAdjustment;

    @Min(value = 0, message = " Stock Quantity must be greater than or equal to 0")
    private Integer stockQuantity;

    @NotBlank(message = "Message can not blank")
    @Length(min = 1, max = 255, message = "Message's length must be greater than 0 and lower or equal to 255")
    private String message;

    public Product getProduct() {
        if (productId == null) {
            return null;
        }
        Product product = new Product();
        product.setId(productId);
        return product;
    }

}
