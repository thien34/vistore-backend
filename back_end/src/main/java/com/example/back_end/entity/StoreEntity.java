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
@Table(name = "store", schema = "public", catalog = "datn")
public class StoreEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @Basic
    @Column(name = "name", nullable = true, length = 255)
    private String name;
    @Basic
    @Column(name = "url", nullable = true, length = 255)
    private String url;
    @Basic
    @Column(name = "hosts", nullable = true, length = 255)
    private String hosts;
    @Basic
    @Column(name = "company_name", nullable = true, length = 255)
    private String companyName;
    @Basic
    @Column(name = "company_address", nullable = true, length = 255)
    private String companyAddress;
    @Basic
    @Column(name = "company_phone", nullable = true, length = 255)
    private String companyPhone;
    @Basic
    @Column(name = "ssl_enabled", nullable = true)
    private Boolean sslEnabled;
    @ManyToOne
    @JoinColumn(name = "default_language_id", nullable = true)
    private LanguageEntity defaultLanguage;
    @Basic
    @Column(name = "display_order", nullable = true)
    private Integer displayOrder;
    @Basic
    @Column(name = "deleted", nullable = true)
    private Boolean deleted;

}
