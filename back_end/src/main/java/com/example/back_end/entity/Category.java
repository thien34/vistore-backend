package com.example.back_end.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "category")
public class Category extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", length = Integer.MAX_VALUE)
    private String name;

    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

    @Column(name = "parent_category_id")
    private Integer parentCategoryId;

    @Column(name = "picture_id")
    private Integer pictureId;

    @Column(name = "show_on_home_page")
    private Boolean showOnHomePage;

    @Column(name = "include_in_top_menu")
    private Boolean includeInTopMenu;

    @Column(name = "page_size")
    private Integer pageSize;

    @Column(name = "published")
    private Boolean published;

    @Column(name = "deleted")
    private Boolean deleted;

    @Column(name = "display_order")
    private Integer displayOrder;

    @Column(name = "price_range_filtering")
    private Boolean priceRangeFiltering;

}