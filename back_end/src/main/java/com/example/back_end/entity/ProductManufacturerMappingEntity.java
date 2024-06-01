package com.example.back_end.entity;


import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "product_manufacturer_mapping", schema = "public", catalog = "datn")
public class ProductManufacturerMappingEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "manufacturer_id", nullable = true)
    private ManufacturerEntity manufacturer;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = true)
    private ProductEntity product;
    @Basic
    @Column(name = "display_order", nullable = true)
    private Integer displayOrder;

}
