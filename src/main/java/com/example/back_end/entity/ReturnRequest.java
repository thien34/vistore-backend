package com.example.back_end.entity;

import com.example.back_end.infrastructure.constant.ReturnRequestStatusType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "return_request")
public class ReturnRequest extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "reason_for_return", length = Integer.MAX_VALUE)
    private String reasonForReturn;

    @Column(name = "request_action", length = Integer.MAX_VALUE)
    private String requestAction;

    @Column(name = "total_return_quantity")
    private Integer totalReturnQuantity;

    @Column(name = "return_fee", precision = 18, scale = 2)
    private BigDecimal returnFee;

    @Column(name = "customer_comments", length = Integer.MAX_VALUE)
    private String customerComments;

    @Column(name = "staff_notes", length = Integer.MAX_VALUE)
    private String staffNotes;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "return_request_status_id")
    private ReturnRequestStatusType returnRequestStatusId;

}