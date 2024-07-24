package com.example.back_end.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "specification_attribute")
public class SpecificationAttribute extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "specification_attribute_group_id")
    private SpecificationAttributeGroup specificationAttributeGroup;

    @Column(name = "name", length = Integer.MAX_VALUE)
    private String name;

    @Column(name = "display_order")
    private Integer displayOrder;
    @OneToMany(mappedBy = "specificationAttribute", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<SpecificationAttributeOption> specificationAttributeOptions;

}