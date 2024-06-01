package com.example.back_end.entity;


import jakarta.persistence.*;

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
@Table(name = "stock_quantity_history", schema = "public", catalog = "datn")
public class StockQuantityHistoryEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = true)
    private ProductEntity product;
    @Basic
    @Column(name = "quantity_adjustment", nullable = true)
    private Integer quantityAdjustment;
    @Basic
    @Column(name = "stock_quantity", nullable = true)
    private Integer stockQuantity;
    @Basic
    @Column(name = "message", nullable = true, length = 255)
    private String message;


}
