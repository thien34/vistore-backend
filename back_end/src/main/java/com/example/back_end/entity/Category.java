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
@Builder
@NoArgsConstructor
@Entity
@Table(name = "category", schema = "public", catalog = "store_db")
public class Category {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = true, length = 255)
    private String name;

    @Column(name = "description", nullable = true, length = 255)
    private String description;

    @Column(name = "parent_category_id", nullable = true)
    private Long parentCategoryId;

    @ManyToOne
    @JoinColumn(name = "picture_id", referencedColumnName = "id")
    private Picture picture;

    @Column(name = "show_on_home_page", nullable = true)
    private Boolean showOnHomePage;

    @Column(name = "include_in_top_menu", nullable = true)
    private Boolean includeInTopMenu;

    @Column(name = "page_size", nullable = true)
    private Integer pageSize;

    @Column(name = "published", nullable = true)
    private Boolean published;

    @Column(name = "deleted", nullable = true)
    private Boolean deleted;

    @Column(name = "display_order", nullable = true)
    private Integer displayOrder;

    @Column(name = "price_range_filtering", nullable = true)
    private Boolean priceRangeFiltering;

}
