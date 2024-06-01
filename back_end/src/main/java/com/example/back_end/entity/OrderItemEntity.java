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
@Table(name = "order_item", schema = "public", catalog = "datn")
public class OrderItemEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = true)
    private OrderEntity order;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = true)
    private ProductEntity product;

    @Column(name = "order_item_guid", nullable = true)
    private String orderItemGuid;
    @Basic
    @Column(name = "quantity", nullable = true)
    private Integer quantity;
    @Basic
    @Column(name = "unit_price", nullable = true, precision = 2)
    private BigDecimal unitPrice;
    @Basic
    @Column(name = "price_total", nullable = true, precision = 2)
    private BigDecimal priceTotal;
    @Basic
    @Column(name = "discount_amount", nullable = true, precision = 2)
    private BigDecimal discountAmount;
    @Basic
    @Column(name = "original_product_cost", nullable = true, precision = 2)
    private BigDecimal originalProductCost;
    @Basic
    @Column(name = "attribute_description", nullable = true, length = 255)
    private String attributeDescription;
    @Basic
    @Column(name = "attributes_json", nullable = true, length = 255)
    private String attributesJson;
    @Basic
    @Column(name = "item_weight", nullable = true, precision = 2)
    private BigDecimal itemWeight;

}
