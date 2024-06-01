package com.example.back_end.entity;




import jakarta.persistence.*;

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
@Table(name = "category", schema = "public", catalog = "datn")
public class CategoryEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @Basic
    @Column(name = "name", nullable = true, length = 255)
    private String name;
    @Basic
    @Column(name = "description", nullable = true, length = 255)
    private String description;
    @Basic
    @Column(name = "parent_category_id", nullable = true)
    private Integer parentCategoryId;
    @Basic
    @Column(name = "picture_id", nullable = true)
    private Integer pictureId;
    @Basic
    @Column(name = "show_on_home_page", nullable = true)
    private Boolean showOnHomePage;
    @Basic
    @Column(name = "include_in_top_menu", nullable = true)
    private Boolean includeInTopMenu;
    @Basic
    @Column(name = "page_size", nullable = true)
    private Integer pageSize;
    @Basic
    @Column(name = "published", nullable = true)
    private Boolean published;
    @Basic
    @Column(name = "deleted", nullable = true)
    private Boolean deleted;
    @Basic
    @Column(name = "display_order", nullable = true)
    private Integer displayOrder;
    @Basic
    @Column(name = "price_range_filtering", nullable = true)
    private Boolean priceRangeFiltering;

}
