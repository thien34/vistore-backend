package com.example.back_end.entity;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
@Table(name = "predefined_product_attribute_value", schema = "public", catalog = "datn")
public class PredefinedProductAttributeValueEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "product_attribute_id", nullable = true)
    private ProductAttributeEntity productAttribute;
    @Basic
    @Column(name = "name", nullable = true, length = 255)
    private String name;
    @Basic
    @Column(name = "price_adjustment", nullable = true, precision = 2)
    private BigDecimal priceAdjustment;
    @Basic
    @Column(name = "price_adjustment_use_percentage", nullable = true)
    private Boolean priceAdjustmentUsePercentage;
    @Basic
    @Column(name = "weight_adjustment", nullable = true, precision = 2)
    private BigDecimal weightAdjustment;
    @Basic
    @Column(name = "cost", nullable = true, precision = 2)
    private BigDecimal cost;
    @Basic
    @Column(name = "is_pre_selected", nullable = true)
    private Boolean isPreSelected;
    @Basic
    @Column(name = "display_order", nullable = true)
    private Integer displayOrder;

}
