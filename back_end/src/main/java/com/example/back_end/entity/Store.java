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
@Table(name = "store", schema = "public", catalog = "store_db")
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = true, length = 255)
    private String name;

    @Column(name = "url", nullable = true, length = 255)
    private String url;

    @Column(name = "hosts", nullable = true, length = 255)
    private String hosts;

    @Column(name = "company_name", nullable = true, length = 255)
    private String companyName;

    @Column(name = "company_address", nullable = true, length = 255)
    private String companyAddress;

    @Column(name = "company_phone", nullable = true, length = 255)
    private String companyPhone;

    @Column(name = "ssl_enabled", nullable = true)
    private Boolean sslEnabled;

    @ManyToOne
    @JoinColumn(name = "default_language_id", nullable = true)
    private Language defaultLanguage;

    @Column(name = "display_order", nullable = true)
    private Integer displayOrder;

    @Column(name = "deleted", nullable = true)
    private Boolean deleted;

}
