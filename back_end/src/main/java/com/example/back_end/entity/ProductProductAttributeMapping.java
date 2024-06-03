package com.example.back_end.entity;

import com.example.back_end.infrastructure.constant.AttributeControlType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "product_product_attribute_mapping")
public class ProductProductAttributeMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "product_attribute_id")
    private ProductAttribute productAttribute;

    @Column(name = "text_prompt", length = Integer.MAX_VALUE)
    private String textPrompt;

    @Column(name = "is_required")
    private Boolean isRequired;

    @Enumerated
    @Column(name = "attribute_control_type_id")
    private AttributeControlType attributeControlTypeId;

    @Column(name = "display_order")
    private Integer displayOrder;

    @Column(name = "validation_min_length")
    private Integer validationMinLength;

    @Column(name = "validation_max_length")
    private Integer validationMaxLength;

    @Column(name = "validation_file_allowed_extensions", length = Integer.MAX_VALUE)
    private String validationFileAllowedExtensions;

    @Column(name = "validation_file_maximum_size")
    private Integer validationFileMaximumSize;

    @Column(name = "default_value", length = Integer.MAX_VALUE)
    private String defaultValue;

}