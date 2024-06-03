package com.example.back_end.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "product_attribute_value")
public class ProductAttributeValue extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "product_attribute_mapping_id")
    private ProductProductAttributeMapping productAttributeMapping;

    @Column(name = "name")
    private String name;

    @Column(name = "color_squares_rgb")
    private String colorSquaresRgb;

    @Column(name = "price_adjustment", precision = 18, scale = 2)
    private BigDecimal priceAdjustment;

    @Column(name = "price_adjustment_percentage")
    private Boolean priceAdjustmentPercentage;

    @Column(name = "weight_adjustment", precision = 18, scale = 2)
    private BigDecimal weightAdjustment;

    @Column(name = "cost", precision = 18, scale = 2)
    private BigDecimal cost;

    @Column(name = "is_pre_selected")
    private Boolean isPreSelected;

    @Column(name = "display_order")
    private Integer displayOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "picture_id")
    private Picture picture;

}