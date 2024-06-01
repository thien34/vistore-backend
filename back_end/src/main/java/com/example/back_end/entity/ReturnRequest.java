package com.example.back_end.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "return_request", schema = "public", catalog = "store_db")
public class ReturnRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "order_item_id", referencedColumnName = "id")
    private OrderItem orderItem;

    @Column(name = "upload_file_id", nullable = true)
    private Integer uploadFileId;

    @ManyToOne
    @JoinColumn(name = "store_id", referencedColumnName = "id")
    private Store storeId;

    @Column(name = "return_request_status_id", nullable = true)
    private Integer returnRequestStatusId;

    @Column(name = "reason_for_return", nullable = true, length = 255)
    private String reasonForReturn;

    @Column(name = "request_action", nullable = true, length = 255)
    private String requestAction;

    @Column(name = "quantity", nullable = true)
    private Integer quantity;

    @Column(name = "return_quantity", nullable = true)
    private Integer returnQuantity;

    @Column(name = "customer_comments", nullable = true, length = 255)
    private String customerComments;

    @Column(name = "staff_notes", nullable = true, length = 255)
    private String staffNotes;

}
