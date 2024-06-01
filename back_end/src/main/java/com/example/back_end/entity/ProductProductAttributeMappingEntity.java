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
@Table(name = "product_product_attribute_mapping", schema = "public", catalog = "datn")
public class ProductProductAttributeMappingEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = true)
    private ProductEntity product;

    @ManyToOne
    @JoinColumn(name = "product_attribute_id", nullable = true)
    private ProductAttributeEntity productAttribute;
    @Basic
    @Column(name = "text", nullable = true, length = 255)
    private String text;
    @Basic
    @Column(name = "is_required", nullable = true)
    private Boolean isRequired;
    @Basic
    @Column(name = "attribute_control_type_id", nullable = true)
    private Integer attributeControlTypeId;
    @Basic
    @Column(name = "display_order", nullable = true)
    private Integer displayOrder;
    @Basic
    @Column(name = "validation_min_length", nullable = true)
    private Integer validationMinLength;
    @Basic
    @Column(name = "validation_max_length", nullable = true)
    private Integer validationMaxLength;
    @Basic
    @Column(name = "validation_file_allowed_extensions", nullable = true, length = 255)
    private String validationFileAllowedExtensions;
    @Basic
    @Column(name = "validation_file_maximum_size", nullable = true)
    private Integer validationFileMaximumSize;
    @Basic
    @Column(name = "default_value", nullable = true, length = 255)
    private String defaultValue;


}
