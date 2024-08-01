package com.example.back_end.repository;

import com.example.back_end.entity.SpecificationAttributeGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecificationAttributeGroupRepository extends JpaRepository<SpecificationAttributeGroup, Long> {
    Page<SpecificationAttributeGroup> findByNameContaining(String name, Pageable pageable);
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, Long id);

}
