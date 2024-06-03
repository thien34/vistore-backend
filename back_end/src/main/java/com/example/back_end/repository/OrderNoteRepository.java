package com.example.back_end.repository;

import com.example.back_end.entity.OrderNote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderNoteRepository extends JpaRepository<OrderNote, Long> {
}