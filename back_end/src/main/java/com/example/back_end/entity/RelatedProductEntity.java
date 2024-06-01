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
@Table(name = "related_product", schema = "public", catalog = "datn")
public class RelatedProductEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "product_id1", nullable = true)
    private ProductEntity product1;

    @ManyToOne
    @JoinColumn(name = "product_id2", nullable = true)
    private ProductEntity product2;
    @Basic
    @Column(name = "display_order", nullable = true)
    private Integer displayOrder;

}
