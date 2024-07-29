package com.example.back_end.core.admin.product.payload.request;

import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ProductAttributeCombinationRequest {

    private Long id;

    @NotBlank
    private String sku;

    private String gtin;

    private Integer stockQuantity;

    private Boolean allowOutOfStockOrders;

    @NotBlank(message = "Error while save attribute combinations. Attribute value not specified.")
    private String attributesXml;

    private BigDecimal overriddenPrice;

    private Integer minStockQuantity;

    private Long productId;

    private List<Long> pictureIds;
    private String manufacturerPartNumber;

}
