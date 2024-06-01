package com.example.back_end.entity;

import com.example.back_end.infrastructure.constant.AttributeControlType;
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
@Table(name = "product_product_attribute_mapping", schema = "public", catalog = "store_db")
public class ProductProductAttributeMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = true)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "product_attribute_id", nullable = true)
    private ProductAttribute productAttribute;

    @Column(name = "text_prompt", nullable = true, length = 255)
    private String textPrompt;

    @Column(name = "is_required", nullable = true)
    private Boolean isRequired;

    @Column(name = "attribute_control_type_id", nullable = true)
    private AttributeControlType attributeControlType;

    @Column(name = "display_order", nullable = true)
    private Integer displayOrder;

    @Column(name = "validation_min_length", nullable = true)
    private Integer validationMinLength;

    @Column(name = "validation_max_length", nullable = true)
    private Integer validationMaxLength;

    @Column(name = "validation_file_allowed_extensions", nullable = true, length = 255)
    private String validationFileAllowedExtensions;

    @Column(name = "validation_file_maximum_size", nullable = true)
    private Integer validationFileMaximumSize;

    @Column(name = "default_value", nullable = true, length = 255)
    private String defaultValue;

}
