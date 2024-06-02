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

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "order_item", schema = "public", catalog = "store_db")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = true)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = true)
    private Product product;

    @Column(name = "order_item_guid", nullable = true)
    private String orderItemGuid;

    @Column(name = "quantity", nullable = true)
    private Integer quantity;

    @Column(name = "unit_price", nullable = true, precision = 2)
    private BigDecimal unitPrice;

    @Column(name = "price_total", nullable = true, precision = 2)
    private BigDecimal priceTotal;

    @Column(name = "discount_amount", nullable = true, precision = 2)
    private BigDecimal discountAmount;

    @Column(name = "original_product_cost", nullable = true, precision = 2)
    private BigDecimal originalProductCost;

    @Column(name = "attribute_description", nullable = true, length = 255)
    private String attributeDescription;

    @Column(name = "attributes_json", nullable = true, length = 255)
    private String attributesJson;

    @Column(name = "item_weight", nullable = true, precision = 2)
    private BigDecimal itemWeight;

}
