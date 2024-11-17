package com.example.back_end.core.admin.order.payload;

import com.example.back_end.entity.OrderStatusHistory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatusHistoryResponse {
    private Long id;
    private Long orderId;
    private Integer status;
    private String notes;
    private Instant paidDate;

    public static OrderStatusHistoryResponse fromOrderStatusHistoryResponse(OrderStatusHistory orderStatusHistory) {
        OrderStatusHistoryResponse orderStatusHistoryResponse = new OrderStatusHistoryResponse();
        orderStatusHistoryResponse.setId(orderStatusHistory.getId());
        orderStatusHistoryResponse.setOrderId(orderStatusHistory.getOrder().getId());
        orderStatusHistoryResponse.setNotes(orderStatusHistory.getNotes());
        orderStatusHistoryResponse.setPaidDate(orderStatusHistory.getPaidDate());
        orderStatusHistoryResponse.setStatus(orderStatusHistory.getStatus().value);
        return orderStatusHistoryResponse;
    }
}