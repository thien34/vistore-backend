package com.example.back_end.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "product_attribute_value", schema = "public", catalog = "store_db")
public class ProductAttributeValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_attribute_mapping_id", nullable = true)
    private ProductProductAttributeMapping productProductAttributeMapping;

    @Column(name = "name", nullable = true)
    private Integer name;

    @Column(name = "color_squares_rgb", nullable = true)
    private Integer colorSquaresRgb;

    @Column(name = "price_adjustment", nullable = true, precision = 2)
    private BigDecimal priceAdjustment;

    @Column(name = "price_adjustment_percentage", nullable = true)
    private Boolean priceAdjustmentPercentage;

    @Column(name = "weight_adjustment", nullable = true, precision = 2)
    private BigDecimal weightAdjustment;

    @Column(name = "cost", nullable = true, precision = 2)
    private BigDecimal cost;

    @Column(name = "is_pre_selected", nullable = true)
    private Boolean isPreSelected;

    @Column(name = "display_order", nullable = true)
    private Integer displayOrder;

    @ManyToOne
    @JoinColumn(name = "picture_id", referencedColumnName = "id", nullable = true)
    private Picture picture;

}
