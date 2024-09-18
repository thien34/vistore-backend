package com.example.back_end.core.admin.product.payload.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Getter
@Setter
public class ProductRequest {

    private Long id;

    @NotEmpty
    private String name;

    private String sku;

    private String manufacturePartNumber;

    private String gtin;

    private String shortDescription;

    private String fullDescription;

    private Boolean showOnHomePage;

    private Boolean allowCustomerReviews;

    private Integer approvedRatingSum;

    private Integer notApprovedRatingSum;

    private Integer approveTotalReviews;

    private Integer notApprovedTotalReviews;

    private Boolean isShipEnabled;

    private Boolean isFreeShipping;

    private Integer manageInventoryMethodId;

    private Integer productAvailabilityRangeId;

    private Boolean displayStockAvailability;

    private Boolean displayStockQuantity;

    private Integer minStockQuantity;

    private Boolean notReturnable;

    private BigDecimal unitPrice;

    private BigDecimal oldPrice;

    private BigDecimal productCost;

    private Boolean markAsNew;

    private Instant markAsNewStartDateTimeUtc;

    private Instant markAsNewEndDateTimeUtc;

    private BigDecimal weight;

    private BigDecimal length;

    private BigDecimal width;

    private BigDecimal height;

    private Instant availableStartDateTimeUtc;

    private Instant availableEndDateTimeUtc;

    private Integer displayOrder;

    private Boolean published;

    private Boolean deleted;

    private List<Long> categoryIds;

    private List<Long> manufacturerIds;

    private List<String> productTags;

    private List<Long> discountIds;

    private Integer minCartQty;

    private Integer maxCartQty;

}
