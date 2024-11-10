package com.example.back_end.repository;

import com.example.back_end.entity.ReturnInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReturnInvoiceRepository extends JpaRepository<ReturnInvoice, Long> {
    @Query(value = "select ri from ReturnInvoice ri where ri.order.id = :invoiceId  ")
    ReturnInvoice findByOrderId(Long invoiceId);
}
