package com.example.back_end.repository;

import com.example.back_end.entity.ReturnRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReturnRequestRepository extends JpaRepository<ReturnRequest, Long> {
    @Query(value = "select rq from ReturnRequest  rq where rq.order.id = :orderId")
    ReturnRequest findByOrderId(Long orderId);

    @Query(value = "select rq from ReturnRequest  rq where rq.customer.id = :customerId")
    List<ReturnRequest> findByCustomerId(Long customerId, Pageable pageable);
}
