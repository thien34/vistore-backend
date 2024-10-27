package com.example.back_end.entity;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "product")
public class Product extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", length = Integer.MAX_VALUE)
    private String name;

    @Column(name = "full_name", length = Integer.MAX_VALUE)
    private String fullName;

    @Column(name = "sku", length = Integer.MAX_VALUE)
    private String sku;

    @Column(name = "gtin", length = Integer.MAX_VALUE)
    private String gtin;

    @Column(name = "full_description", length = Integer.MAX_VALUE)
    private String fullDescription;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "unit_price", precision = 18, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "product_cost", precision = 18, scale = 2)
    private BigDecimal productCost;

    @Column(name = "weight", precision = 18, scale = 2)
    private BigDecimal weight;

    @Column(name = "published")
    private Boolean published;

    @Column(name = "deleted")
    private Boolean deleted;

    @Column(name = "parent_product_id")
    private Long parentProductId;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "manufacturer_id")
    private Manufacturer manufacturer;

    @Column(name = "image")
    private String image;

    @JsonManagedReference
    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    private List<ProductSpecificationAttributeMapping> productSpecificationAttributeMappings;

    @JsonManagedReference
    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    private List<ProductProductTagMapping> productProductTagMappings;

    @JsonManagedReference
    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    private List<ProductPictureMapping> productPictureMappings;

    @JsonManagedReference
    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    private List<ProductAttributeValue> productAttributeValues;

}