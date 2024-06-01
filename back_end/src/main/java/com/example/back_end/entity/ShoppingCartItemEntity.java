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
@Table(name = "shopping_cart_item", schema = "public", catalog = "datn")
public class ShoppingCartItemEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = true)
    private ProductEntity product;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = true)
    private CustomerEntity customer;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = true)
    private StoreEntity store;

    @Basic
    @Column(name = "shopping_cart_type_id", nullable = true)
    private Integer shoppingCartTypeId;
    @Basic
    @Column(name = "attribute_json", nullable = true, length = 255)
    private String attributeJson;
    @Basic
    @Column(name = "quantity", nullable = true)
    private Integer quantity;


}
