package com.example.back_end.entity;

import com.example.back_end.infrastructure.constant.DiscountLimitationType;
import com.example.back_end.infrastructure.constant.DiscountType;
import com.example.back_end.infrastructure.utils.DiscountLimitationTypeConverter;
import com.example.back_end.infrastructure.utils.DiscountTypeConverter;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "discount")
public class Discount extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Convert(converter = DiscountTypeConverter.class)
    @Column(name = "discount_type_id")
    private DiscountType discountTypeId;

    @Convert(converter = DiscountLimitationTypeConverter.class)
    @Column(name = "discount_limitation_id")
    private DiscountLimitationType discountLimitationId;

    @Column(name = "name", length = Integer.MAX_VALUE)
    private String name;

    @Column(name = "coupon_code", length = Integer.MAX_VALUE)
    private String couponCode;

    @Column(name = "comment", length = Integer.MAX_VALUE)
    private String comment;

    @Column(name = "use_percentage")
    private Boolean usePercentage;

    @Column(name = "discount_percentage", precision = 18, scale = 2)
    private BigDecimal discountPercentage;

    @Column(name = "discount_amount", precision = 18, scale = 2)
    private BigDecimal discountAmount;

    @Column(name = "max_discount_amount", precision = 18, scale = 2)
    private BigDecimal maxDiscountAmount;

    @Column(name = "start_date_utc")
    private Instant startDateUtc;

    @Column(name = "end_date_utc")
    private Instant endDateUtc;

    @Column(name = "requires_coupon_code")
    private Boolean requiresCouponCode;

    @Column(name = "is_cumulative")
    private Boolean isCumulative;

    @Column(name = "limitation_times")
    private Integer limitationTimes;

    @Column(name = "applied_to_sub_categories")
    private Boolean appliedToSubCategories;

    @Column(name = "min_oder_amount", precision = 18, scale = 2)
    private BigDecimal minOderAmount;

    @Column(name = "is_canceled")
    private Boolean isCanceled;

    @Column(name = "status")
    private String status;

    @Column(name = "is_published")
    private Boolean isPublished = false;

    @Column(name = "usage_count")
    private Integer usageCount;

    @Column(name = "per_customer_limit")
    private Integer perCustomerLimit;

    @JsonManagedReference
    @OneToMany(mappedBy = "discount", cascade = CascadeType.REMOVE)
    private List<DiscountAppliedToProduct> appliedToProducts;
}