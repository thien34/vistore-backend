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
@Table(name = "product_review", schema = "public", catalog = "datn")
public class ProductReviewEntity {
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

    @ManyToOne
    @JoinColumn(name = "product_review_parent_id", nullable = true)
    private ProductReviewEntity productReviewParent;

    @Basic
    @Column(name = "is_approved", nullable = true)
    private Boolean isApproved;
    @Basic
    @Column(name = "title", nullable = true, length = 255)
    private String title;
    @Basic
    @Column(name = "review_varchar(255)", nullable = true, length = 255)
    private String reviewVarchar255;
    @Basic
    @Column(name = "rating", nullable = true)
    private Integer rating;
    @Basic
    @Column(name = "helpful_yes_total", nullable = true)
    private Integer helpfulYesTotal;
    @Basic
    @Column(name = "helpful_no_total", nullable = true)
    private Integer helpfulNoTotal;

}
