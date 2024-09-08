package com.example.back_end.repository;

import com.example.back_end.entity.ProductTag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ProductTagRepository extends JpaRepository<ProductTag, Long> {

    Page<ProductTag> findByNameContaining(String name, Pageable pageable);

    ProductTag findByName(String name);

    List<ProductTag> findByNameIn(Set<String> tagNames);
}
