package com.example.back_end.entity;


import jakarta.persistence.*;

import java.util.Objects;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
@Table(name = "return_request", schema = "public", catalog = "datn")
public class ReturnRequestEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @Basic
    @Column(name = "customer_id", nullable = true)
    private Integer customerId;
    @Basic
    @Column(name = "order_item_id", nullable = true)
    private Integer orderItemId;
    @Basic
    @Column(name = "upload_file_id", nullable = true)
    private Integer uploadFileId;
    @Basic
    @Column(name = "store_id", nullable = true)
    private Integer storeId;
    @Basic
    @Column(name = "return_request_status_id", nullable = true)
    private Integer returnRequestStatusId;
    @Basic
    @Column(name = "reason_for_return", nullable = true, length = 255)
    private String reasonForReturn;
    @Basic
    @Column(name = "request_action", nullable = true, length = 255)
    private String requestAction;
    @Basic
    @Column(name = "quantity", nullable = true)
    private Integer quantity;
    @Basic
    @Column(name = "return_quantity", nullable = true)
    private Integer returnQuantity;
    @Basic
    @Column(name = "customer_comments", nullable = true, length = 255)
    private String customerComments;
    @Basic
    @Column(name = "staff_notes", nullable = true, length = 255)
    private String staffNotes;

}
