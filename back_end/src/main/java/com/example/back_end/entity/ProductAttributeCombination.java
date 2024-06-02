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
@Table(name = "product_attribute_combination", schema = "public", catalog = "store_db")
public class ProductAttributeCombination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = true)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "picture_id", nullable = true)
    private Picture picture;

    @Column(name = "sku", nullable = true, length = 255)
    private String sku;

    @Column(name = "gtin", nullable = true, length = 255)
    private String gtin;

    @Column(name = "attributes_xml", nullable = true, length = 255)
    private String attributesXml;

    @Column(name = "manufacturer_part_number", nullable = true, length = 255)
    private String manufacturerPartNumber;

    @Column(name = "stock_quantity", nullable = true)
    private Integer stockQuantity;

    @Column(name = "allow_out_of_stock_orders", nullable = true)
    private Boolean allowOutOfStockOrders;

    @Column(name = "overridden_price", nullable = true, precision = 2)
    private BigDecimal overriddenPrice;

    @Column(name = "min_stock_quantity", nullable = true)
    private Integer minStockQuantity;

}
