package com.example.back_end.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "localized_property")
public class LocalizedProperty {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "localized_property_id_gen")
    @SequenceGenerator(name = "localized_property_id_gen", sequenceName = "localized_property_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "locale_key_group", length = Integer.MAX_VALUE)
    private String localeKeyGroup;

    @Column(name = "locale_key", length = Integer.MAX_VALUE)
    private String localeKey;

    @Column(name = "locale_value", length = Integer.MAX_VALUE)
    private String localeValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "language_id")
    private Language language;

}