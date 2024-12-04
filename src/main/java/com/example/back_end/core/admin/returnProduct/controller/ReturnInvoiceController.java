package com.example.back_end.core.admin.returnProduct.controller;

import com.example.back_end.core.admin.returnProduct.payload.request.ReturnInvoiceRequest;
import com.example.back_end.core.admin.returnProduct.payload.response.ReturnInvoiceResponse;
import com.example.back_end.core.common.PageRequest;
import com.example.back_end.core.common.PageResponse1;
import com.example.back_end.core.common.ResponseData;
import com.example.back_end.service.returnProducts.ReturnInvoiceServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/return-invoice")
public class ReturnInvoiceController {
    private final ReturnInvoiceServices returnInvoiceServices;

    @PostMapping
    public ResponseData<ReturnInvoiceResponse> saveReturnInvoice(@RequestBody ReturnInvoiceRequest request) {
        ReturnInvoiceResponse response = returnInvoiceServices.saveReturnInvoice(request);
        return ResponseData.<ReturnInvoiceResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Hóa đơn trả lại mới đã cứu thành công!")
                .data(response).build();
    }

    @GetMapping("/{id}")
    public ResponseData<ReturnInvoiceResponse> getReturnInvoice(@PathVariable Long id) {
        ReturnInvoiceResponse response = returnInvoiceServices.getReturnInvoiceById(id);
        return ResponseData.<ReturnInvoiceResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Nhận hóa đơn trả lại có ID: " + id + " thành công!")
                .data(response).build();
    }

    @GetMapping("/order/{orderId}")
    public ResponseData<ReturnInvoiceResponse> getReturnInvoiceByOrderId(@PathVariable Long orderId) {
        ReturnInvoiceResponse response = returnInvoiceServices.getReturnInvoiceByOrderId(orderId);
        return ResponseData.<ReturnInvoiceResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Nhận hóa đơn trả lại với ID đơn hàng: " + orderId + " thành công!")
                .data(response).build();
    }

    @GetMapping("")
    public ResponseData<PageResponse1<List<ReturnInvoiceResponse>>> getAllReturnInvoices(PageRequest pageRequest) {
        PageResponse1<List<ReturnInvoiceResponse>> response = returnInvoiceServices.getAllReturnInvoices(pageRequest);
        return ResponseData.<PageResponse1<List<ReturnInvoiceResponse>>>builder()
                .status(HttpStatus.OK.value())
                .message("Nhận tất cả các hóa đơn sản phẩm trả lại thành công!")
                .data(response).build();
    }
}
