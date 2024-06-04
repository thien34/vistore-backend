package com.example.back_end.repository;

import com.example.back_end.entity.LocaleStringResource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocaleStringResourceRepository extends JpaRepository<LocaleStringResource, Long> {
    List<LocaleStringResource> findByLanguageName(String languageName);
}