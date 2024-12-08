package com.example.back_end.core.admin.order.payload;

import com.example.back_end.entity.OrderStatusHistory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatusHistoryResponse {
    private Long id;
    private Long orderId;
    private Integer status;
    private String notes;
    private Instant paidDate;
    private String createdBy;
    private String updatedBy;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public static OrderStatusHistoryResponse fromOrderStatusHistoryResponse(OrderStatusHistory orderStatusHistory, String createdBy, String updatedBy) {
        OrderStatusHistoryResponse orderStatusHistoryResponse = new OrderStatusHistoryResponse();
        orderStatusHistoryResponse.setId(orderStatusHistory.getId());
        orderStatusHistoryResponse.setOrderId(orderStatusHistory.getOrder().getId());
        orderStatusHistoryResponse.setNotes(orderStatusHistory.getNotes());
        orderStatusHistoryResponse.setPaidDate(orderStatusHistory.getPaidDate());
        orderStatusHistoryResponse.setStatus(orderStatusHistory.getStatus().value);
        orderStatusHistoryResponse.setCreatedBy(createdBy);
        orderStatusHistoryResponse.setUpdatedBy(updatedBy);
        orderStatusHistoryResponse.setCreatedDate(orderStatusHistory.getCreatedDate());
        orderStatusHistoryResponse.setUpdatedDate(orderStatusHistory.getLastModifiedDate());
        return orderStatusHistoryResponse;
    }
}