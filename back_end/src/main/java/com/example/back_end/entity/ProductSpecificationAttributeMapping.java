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

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "product_specification_attribute_mapping", schema = "public", catalog = "store_db")
public class ProductSpecificationAttributeMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = true)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "specification_attribute_option_id", nullable = true)
    private SpecificationAttributeOption specificationAttributeOption;

    @Column(name = "custom_value", nullable = true, length = 255)
    private String customValue;

    @Column(name = "show_on_product_page", nullable = true)
    private Boolean showOnProductPage;

    @Column(name = "display_order", nullable = true)
    private Integer displayOrder;

}
