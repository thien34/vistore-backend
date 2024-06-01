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
@Table(name = "picture", schema = "public", catalog = "datn")
public class PictureEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @Basic
    @Column(name = "mime_type", nullable = true, length = 255)
    private String mimeType;
    @Basic
    @Column(name = "link_img", nullable = true, length = 255)
    private String linkImg;
    @Basic
    @Column(name = "seo_file_name", nullable = true, length = 255)
    private String seoFileName;
    @Basic
    @Column(name = "alt_attribute", nullable = true, length = 255)
    private String altAttribute;
    @Basic
    @Column(name = "title_attribute", nullable = true, length = 255)
    private String titleAttribute;
    @Basic
    @Column(name = "is_new", nullable = true)
    private Boolean isNew;
    @Basic
    @Column(name = "virtual_path", nullable = true, length = 255)
    private String virtualPath;

}
