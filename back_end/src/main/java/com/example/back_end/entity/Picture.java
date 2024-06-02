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
@Table(name = "picture")
public class Picture extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "mime_type", length = Integer.MAX_VALUE)
    private String mimeType;

    @Column(name = "link_img", length = Integer.MAX_VALUE)
    private String linkImg;

    @Column(name = "seo_file_name", length = Integer.MAX_VALUE)
    private String seoFileName;

    @Column(name = "alt_attribute", length = Integer.MAX_VALUE)
    private String altAttribute;

    @Column(name = "title_attribute", length = Integer.MAX_VALUE)
    private String titleAttribute;

    @Column(name = "is_new")
    private Boolean isNew;

    @Column(name = "virtual_path", length = Integer.MAX_VALUE)
    private String virtualPath;

}