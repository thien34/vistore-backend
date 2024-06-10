package com.example.back_end.repository;

import com.example.back_end.entity.LocalizedProperty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocalizedPropertyRepository extends JpaRepository<LocalizedProperty, Long> {

    List<LocalizedProperty> findByLanguageName(String languageName);

}
