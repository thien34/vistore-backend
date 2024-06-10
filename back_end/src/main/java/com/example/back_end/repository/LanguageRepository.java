package com.example.back_end.repository;

import com.example.back_end.entity.Language;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LanguageRepository extends JpaRepository<Language, Long> {

    Optional<Language> findByName(String languageName);

}
