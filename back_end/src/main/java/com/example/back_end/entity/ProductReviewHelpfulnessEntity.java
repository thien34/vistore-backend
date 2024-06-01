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
@Table(name = "product_review_helpfulness", schema = "public", catalog = "datn")
public class ProductReviewHelpfulnessEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "product_review_id", nullable = true)
    private ProductReviewEntity productReview;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = true)
    private CustomerEntity customer;
    @Basic
    @Column(name = "was_helpful", nullable = true)
    private Boolean wasHelpful;

}
