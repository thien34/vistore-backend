package com.example.back_end.repository;

import com.example.back_end.entity.SpecificationAttribute;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface SpecificationAttributeRepository extends JpaRepository<SpecificationAttribute, Long> {
    Page<SpecificationAttribute> findByNameContaining(String name, Pageable pageable);
}