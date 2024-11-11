package com.example.back_end.core.admin.returnProduct.controller;

import com.example.back_end.core.admin.returnProduct.payload.request.ReturnItemRequest;
import com.example.back_end.core.admin.returnProduct.payload.response.ReturnItemResponse;
import com.example.back_end.core.common.PageRequest;
import com.example.back_end.core.common.PageResponse1;
import com.example.back_end.core.common.ResponseData;
import com.example.back_end.service.returnProducts.ReturnItemServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/return-item")
public class ReturnItemController {

    private final ReturnItemServices services;

    @PostMapping()
    public ResponseData<List<ReturnItemResponse>> saveReturnItems(@RequestBody List<ReturnItemRequest> requests) {
        List<ReturnItemResponse> responses = services.addReturnItems(requests);
        return ResponseData.<List<ReturnItemResponse>>builder()
                .status(HttpStatus.OK.value())
                .message(responses.size() + " Return Items saved success!")
                .data(responses).build();
    }

    @GetMapping("/{id}")
    public ResponseData<ReturnItemResponse> getReturnItem(@PathVariable Long id) {
        ReturnItemResponse response = services.getReturnItemById(id);
        return ResponseData.<ReturnItemResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Get Return Item with id: " + id + " success!")
                .data(response).build();
    }

    @GetMapping("")
    public ResponseData<PageResponse1<List<ReturnItemResponse>>> getReturnItemByReturnRequestId(@RequestParam Long returnRequestId, PageRequest pageRequest) {
        PageResponse1<List<ReturnItemResponse>> responses = services.getAllReturnItemsByReturnRequestId(returnRequestId, pageRequest);
        return ResponseData.<PageResponse1<List<ReturnItemResponse>>>builder()
                .status(HttpStatus.OK.value())
                .message("Get Return Items with Return Request Id : " + returnRequestId + " success!")
                .data(responses).build();
    }
}
