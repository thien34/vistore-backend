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
@Table(name = "product_attribute_combination", schema = "public", catalog = "datn")
public class ProductAttributeCombinationEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = true)
    private ProductEntity product;

    @ManyToOne
    @JoinColumn(name = "picture_id", nullable = true)
    private PictureEntity picture;

    @Basic
    @Column(name = "sku", nullable = true, length = 255)
    private String sku;
    @Basic
    @Column(name = "gtin", nullable = true, length = 255)
    private String gtin;
    @Basic
    @Column(name = "attributes_xml", nullable = true, length = 255)
    private String attributesXml;
    @Basic
    @Column(name = "manufacturer_part_number", nullable = true, length = 255)
    private String manufacturerPartNumber;
    @Basic
    @Column(name = "stock_quantity", nullable = true)
    private Integer stockQuantity;
    @Basic
    @Column(name = "allow_out_of_stock_orders", nullable = true)
    private Boolean allowOutOfStockOrders;
    @Basic
    @Column(name = "overridden_price", nullable = true, precision = 2)
    private BigDecimal overriddenPrice;
    @Basic
    @Column(name = "min_stock_quantity", nullable = true)
    private Integer minStockQuantity;


}
