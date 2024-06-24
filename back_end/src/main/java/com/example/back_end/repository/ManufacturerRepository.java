package com.example.back_end.repository;

import com.example.back_end.entity.Manufacturer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {
    @Query(value = "select mn from Manufacturer mn  where mn.deleted = false and (:name is null or mn.name like %:name%) and (:published is null or mn.published = :published) ")
    Page<Manufacturer> findManufacturer(@Param("name") String name, @Param("published") Boolean published, Pageable pageable);
    @Modifying
    @Query(value = "Update Manufacturer mn set mn.deleted=true where mn.id = :id")
    void deleteManufacturer(@Param("id") Long id);
}