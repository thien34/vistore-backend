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

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "product_review", schema = "public", catalog = "store_db")
public class ProductReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = true)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = true)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = true)
    private Store store;

    @ManyToOne
    @JoinColumn(name = "product_review_parent_id", nullable = true)
    private ProductReview productReviewParent;

    @Column(name = "is_approved", nullable = true)
    private Boolean isApproved;

    @Column(name = "title", nullable = true, length = 255)
    private String title;

    @Column(name = "review_text", nullable = true, length = 255)
    private String reviewText;

    @Column(name = "rating", nullable = true)
    private Integer rating;

    @Column(name = "helpful_yes_total", nullable = true)
    private Integer helpfulYesTotal;

    @Column(name = "helpful_no_total", nullable = true)
    private Integer helpfulNoTotal;

}
