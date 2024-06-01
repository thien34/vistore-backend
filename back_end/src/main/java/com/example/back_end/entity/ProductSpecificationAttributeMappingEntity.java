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
@Table(name = "product_specification_attribute_mapping", schema = "public", catalog = "datn")
public class ProductSpecificationAttributeMappingEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = true)
    private ProductEntity product;

    @ManyToOne
    @JoinColumn(name = "specification_attribute_option_id", nullable = true)
    private SpecificationAttributeOptionEntity specificationAttributeOption;
    @Basic
    @Column(name = "custom_value", nullable = true, length = 255)
    private String customValue;
    @Basic
    @Column(name = "show_on_product_page", nullable = true)
    private Boolean showOnProductPage;
    @Basic
    @Column(name = "display_order", nullable = true)
    private Integer displayOrder;


}
