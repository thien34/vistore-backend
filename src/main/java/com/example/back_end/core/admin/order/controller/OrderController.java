package com.example.back_end.core.admin.order.controller;

import com.example.back_end.core.admin.order.payload.CustomerOrderResponse;
import com.example.back_end.core.admin.order.payload.InvoiceData;
import com.example.back_end.core.admin.order.payload.OrderCustomerResponse;
import com.example.back_end.core.admin.order.payload.OrderFilter;
import com.example.back_end.core.admin.order.payload.OrderItemSummary;
import com.example.back_end.core.admin.order.payload.OrderItemsResponse;
import com.example.back_end.core.admin.order.payload.OrderRequest;
import com.example.back_end.core.admin.order.payload.OrderResponse;
import com.example.back_end.core.admin.order.payload.OrderStatusHistoryResponse;
import com.example.back_end.core.common.PageRequest;
import com.example.back_end.core.common.PageResponse1;
import com.example.back_end.core.common.ResponseData;
import com.example.back_end.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseData<Void> saveOrder(@RequestBody OrderRequest orderRequest) {
        try {
            orderService.saveOrder(orderRequest);
        } catch (BadRequestException e) {
            throw new RuntimeException(e);
        }
        return new ResponseData<>(HttpStatus.OK.value(), "Lưu đơn hàng thành công");
    }

    @GetMapping
    public ResponseData<List<OrderResponse>> getAllOrders(OrderFilter filter) {
        List<OrderResponse> orders = orderService.getOrders(filter);
        return new ResponseData<>(HttpStatus.OK.value(), "Lấy đơn hàng thành công", orders);
    }

    @GetMapping("/{orderId}/order-items")
    public ResponseData<List<OrderItemsResponse>> getOrderItems(@PathVariable("orderId") Long orderId) {
        List<OrderItemsResponse> responses = orderService.getOrderItemsByOrderId(orderId);
        return new ResponseData<>(HttpStatus.OK.value(), "Tìm nạp đơn hàng thành công", responses);
    }

    @GetMapping("/{orderId}/order-status-history")
    public ResponseData<List<OrderStatusHistoryResponse>> getOrderHist(@PathVariable("orderId") Long orderId) {
        List<OrderStatusHistoryResponse> responses = orderService.getOrderStatusHistory(orderId);
        return new ResponseData<>(HttpStatus.OK.value(), "Tìm nạp lịch sử trạng thái đơn hàng thành công", responses);
    }

    @GetMapping("/{orderId}/order-items-summary")
    public ResponseData<List<OrderItemSummary>> getOrderItemsSummary(@PathVariable("orderId") Long orderId) {
        List<OrderItemSummary> responses = orderService.getAllOrderItemSummaryByOrderId(orderId);
        return new ResponseData<>(HttpStatus.OK.value(), "Tìm nạp tất cả các mục đơn hàng thành công", responses);
    }


    @GetMapping("/customer-orders")
    public ResponseData<PageResponse1<List<CustomerOrderResponse>>> getCustomerOrders(PageRequest pageRequest) {
        PageResponse1<List<CustomerOrderResponse>> response = orderService.getCustomerOrders(pageRequest);
        return ResponseData.<PageResponse1<List<CustomerOrderResponse>>>builder()
                .status(HttpStatus.OK.value())
                .message("Lấy tất cả đơn hàng thành công!")
                .data(response).build();
    }

    @PutMapping("/updateQuantity/{id}")
    public ResponseData<Void> updateOrderItem(@RequestParam Integer quantity, @PathVariable Long id) {
        orderService.updateQuantity(id, quantity);
        return new ResponseData<>(HttpStatus.OK.value(), "Cập nhật đơn hàng thành công");
    }

    @PutMapping("/addMoreProduct/{id}")
    public ResponseData<Void> addProductToOrder(@RequestBody OrderRequest.OrderItemRequest itemRequest, @PathVariable Long id) {
        orderService.addProductToOrder(itemRequest, id);
        return new ResponseData<>(HttpStatus.OK.value(), "Thêm sản phẩm vào đơn hàng thành công");
    }

    @GetMapping("/change-status/{status}")
    public ResponseData<Void> changeStatus(@PathVariable Integer status,
                                           @RequestParam(name = "reason") String reason,
                                           @RequestParam(name = "orderId") Long orderId) {
        orderService.changeStatus(status, reason, orderId);
        return new ResponseData<>(HttpStatus.OK.value(), "Thay đổi trạng thái đơn hàng thành công");
    }

    @GetMapping("/customer/order/{orderId}")
    public ResponseData<OrderCustomerResponse> getCustomerOrder(@PathVariable Long orderId) {
        return new ResponseData<>(HttpStatus.OK.value(), "Tìm nạp đơn hàng của khách hàng thành công", orderService.getCustomerById(orderId));
    }

    @GetMapping("/discounts/{orderId}")
    public ResponseData<List<String>> getDiscounts(@PathVariable Long orderId) {
        return new ResponseData<>(HttpStatus.OK.value(), "Lấy phiếu giảm giá thành công", orderService.getDiscountByOrderId(orderId));
    }

    @GetMapping("/cancel-order/{orderId}")
    public ResponseData<Void> cancelOrder(@PathVariable Long orderId, @RequestParam String note) {
        orderService.cancelOrder(orderId, note);
        return new ResponseData<>(HttpStatus.OK.value(), "Hủy đơn hàng thành công");
    }

    @GetMapping("/invoice/{orderId}")
    public ResponseData<InvoiceData> getInvoice(@PathVariable Long orderId) {
        return new ResponseData<>(HttpStatus.OK.value(),"Lấy hóa đơn thành công", orderService.getByOrderId(orderId));
    }


}
