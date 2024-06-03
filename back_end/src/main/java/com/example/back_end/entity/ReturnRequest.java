package com.example.back_end.entity;

import com.example.back_end.infrastructure.constant.ReturnRequestStatusType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "order_item_id")
    private OrderItem orderItem;

    @Column(name = "upload_file_id")
    private Integer uploadFileId;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "store_id")
    private Store store;

    @Enumerated
    @Column(name = "return_request_status_id")
    private ReturnRequestStatusType returnRequestStatusId;

    @Column(name = "reason_for_return", length = Integer.MAX_VALUE)
    private String reasonForReturn;

    @Column(name = "request_action", length = Integer.MAX_VALUE)
    private String requestAction;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "return_quantity")
    private Integer returnQuantity;

    @Column(name = "customer_comments", length = Integer.MAX_VALUE)
    private String customerComments;

    @Column(name = "staff_notes", length = Integer.MAX_VALUE)
    private String staffNotes;

}