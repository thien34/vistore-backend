package com.example.back_end.repository;

import com.example.back_end.entity.ReturnTimeLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReturnTimeLineRepository extends JpaRepository<ReturnTimeLine, Integer> {
    @Query("select rtl from ReturnTimeLine rtl where rtl.returnRequest.id = :returnRequestId ")
    List<ReturnTimeLine> getAllReturnTimeLinesByReturnRequestId(Long returnRequestId);
}
