package com.example.back_end.repository;

import com.example.back_end.entity.SpecificationAttributeOption;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpecificationAttributeOptionRepository extends JpaRepository<SpecificationAttributeOption, Long> {
    Page<SpecificationAttributeOption> findByNameContaining(String name, Pageable pageable);
    List<SpecificationAttributeOption> findBySpecificationAttributeId(Long specificationAttributeId);
}
