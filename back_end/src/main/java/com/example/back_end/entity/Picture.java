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
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "picture", schema = "public", catalog = "store_db")
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "mime_type", nullable = true, length = 255)
    private String mimeType;

    @Column(name = "link_img", nullable = true, length = 255)
    private String linkImg;

    @Column(name = "seo_file_name", nullable = true, length = 255)
    private String seoFileName;

    @Column(name = "alt_attribute", nullable = true, length = 255)
    private String altAttribute;

    @Column(name = "title_attribute", nullable = true, length = 255)
    private String titleAttribute;

    @Column(name = "is_new", nullable = true)
    private Boolean isNew;

    @Column(name = "virtual_path", nullable = true, length = 255)
    private String virtualPath;

}
