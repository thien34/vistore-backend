package com.example.back_end.core.admin.returnProduct.controller;

import com.example.back_end.core.admin.returnProduct.payload.request.PendingReturnItemRequest;
import com.example.back_end.core.admin.returnProduct.payload.request.ProcessedReturnItemRequest;
import com.example.back_end.core.admin.returnProduct.payload.response.PendingReturnItemResponse;
import com.example.back_end.core.admin.returnProduct.payload.response.ProcessedReturnItemResponse;
import com.example.back_end.core.common.PageRequest;
import com.example.back_end.core.common.PageResponse1;
import com.example.back_end.core.common.ResponseData;
import com.example.back_end.service.returnProducts.ReturnProductServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/return-product")
public class ReturnProductController {
    private final ReturnProductServices returnProductServices;

    @PostMapping("/pending-return-item")
    public ResponseData<Void> savePendingReturnItems(@RequestBody List<PendingReturnItemRequest> pendingReturnItems) {
        returnProductServices.createPendingReturnItem(pendingReturnItems);
        return new ResponseData<>(HttpStatus.OK.value(), "Lưu thành công mặt hàng trả lại đang chờ xử lý");
    }

    @PostMapping("/processed-return-item")
    public ResponseData<Void> saveProcessedReturnItems(@RequestBody List<ProcessedReturnItemRequest> processedReturnItems) {
        returnProductServices.createProcessReturnItem(processedReturnItems);
        return new ResponseData<>(HttpStatus.OK.value(), "Lưu thành công mặt hàng trả lại đã xử lý");
    }

    @GetMapping("/pending-return-item")
    public ResponseData<PageResponse1<List<PendingReturnItemResponse>>> getPendingReturnItems(PageRequest pageRequest) {
        PageResponse1<List<PendingReturnItemResponse>> responses = returnProductServices.getPendingReturnItems(pageRequest);
        return ResponseData.<PageResponse1<List<PendingReturnItemResponse>>>builder()
                .status(HttpStatus.OK.value())
                .message("Nhận thành công tất cả các mặt hàng trả lại đang chờ xử lý!")
                .data(responses).build();
    }

    @GetMapping("/processed-return-item")
    public ResponseData<PageResponse1<List<ProcessedReturnItemResponse>>> getProcessedReturnItems(PageRequest pageRequest) {
        PageResponse1<List<ProcessedReturnItemResponse>> responses = returnProductServices.getProcessReturnItems(pageRequest);
        return ResponseData.<PageResponse1<List<ProcessedReturnItemResponse>>>builder()
                .status(HttpStatus.OK.value())
                .message("Nhận thành công tất cả các mặt hàng trả lại đang chờ xử lý!")
                .data(responses).build();
    }
}
