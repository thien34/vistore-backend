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
@Table(name = "product_attribute_combination")
public class ProductAttributeCombination extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "picture_id")
    private Picture picture;

    @Column(name = "sku", length = Integer.MAX_VALUE)
    private String sku;

    @Column(name = "gtin", length = Integer.MAX_VALUE)
    private String gtin;

    @Column(name = "attributes_xml", length = Integer.MAX_VALUE)
    private String attributesXml;

    @Column(name = "manufacturer_part_number", length = Integer.MAX_VALUE)
    private String manufacturerPartNumber;

    @Column(name = "stock_quantity")
    private Integer stockQuantity;

    @Column(name = "allow_out_of_stock_orders")
    private Boolean allowOutOfStockOrders;

    @Column(name = "overridden_price", precision = 18, scale = 2)
    private BigDecimal overriddenPrice;

    @Column(name = "min_stock_quantity")
    private Integer minStockQuantity;

}