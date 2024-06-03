package com.example.back_end.repository;

import com.example.back_end.entity.ExternalAuthenticationRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExternalAuthenticationRecordRepository extends JpaRepository<ExternalAuthenticationRecord, Long> {
}