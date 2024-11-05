package com.example.back_end.entity;

import com.example.back_end.infrastructure.constant.ShoppingCartType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "shopping_cart_item")
public class ShoppingCartItem {
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
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Enumerated
    @Column(name = "shopping_cart_type_id")
    private ShoppingCartType shoppingCartTypeId;

    @Column(name = "attribute_json", length = Integer.MAX_VALUE)
    private String attributeJson;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "cart_uuid")
    private String cartUUID;

    @Column(name = "is_admin")
    private Boolean isAdmin;

    @Column(name = "parent_id")
    private String parentId;

    @Column(name = "bill_quantity")
    private Long billQuantity;

}