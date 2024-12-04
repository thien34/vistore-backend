package com.example.back_end.core.admin.returnProduct.controller;

import com.example.back_end.core.admin.returnProduct.payload.request.ReturnRequestRequest;
import com.example.back_end.core.admin.returnProduct.payload.request.ReturnTimeLineRequest;
import com.example.back_end.core.admin.returnProduct.payload.response.ReturnRequestResponse;
import com.example.back_end.core.admin.returnProduct.payload.response.ReturnTimeLineResponse;
import com.example.back_end.core.common.PageRequest;
import com.example.back_end.core.common.PageResponse1;
import com.example.back_end.core.common.ResponseData;
import com.example.back_end.service.returnProducts.ReturnRequestServices;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@RequestMapping("/admin/return-request")
public class ReturnRequestController {
    private final ReturnRequestServices returnRequestServices;

    @GetMapping("")
    public ResponseData<PageResponse1<List<ReturnRequestResponse>>> getAllReturnRequests(PageRequest pageRequest) {
        PageResponse1<List<ReturnRequestResponse>> response = returnRequestServices.getAllReturnRequests(pageRequest);
        return ResponseData.<PageResponse1<List<ReturnRequestResponse>>>builder()
                .status(HttpStatus.OK.value())
                .message("Nhận tất cả các yêu cầu trả lại thành công!")
                .data(response).build();
    }

    @GetMapping("/customer")
    public ResponseData<PageResponse1<List<ReturnRequestResponse>>> getAllReturnRequestsByCustomerId(@RequestParam Long customerId, PageRequest pageRequest) {
        PageResponse1<List<ReturnRequestResponse>> response = returnRequestServices.getAllReturnRequestsByCustomerId(customerId, pageRequest);
        return ResponseData.<PageResponse1<List<ReturnRequestResponse>>>builder()
                .status(HttpStatus.OK.value())
                .message("Nhận tất cả các yêu cầu trả lại bằng CustomerId: " + customerId + " thành công!")
                .data(response).build();
    }

    @GetMapping("/order")
    public ResponseData<ReturnRequestResponse> getAllReturnRequestsByOrderId(@RequestParam Long orderId) {
        ReturnRequestResponse response = returnRequestServices.getReturnRequestByOrderId(orderId);
        return ResponseData.<ReturnRequestResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Nhận yêu cầu trả lại bằng OrderId: " + orderId + " thành công!")
                .data(response).build();
    }

    @GetMapping("/{id}")
    public ResponseData<ReturnRequestResponse> getAllReturnRequestsById(@PathVariable Long id) {
        ReturnRequestResponse response = returnRequestServices.getReturnRequestById(id);
        return ResponseData.<ReturnRequestResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Nhận yêu cầu trả lại theo Id: " + id + " thành công!")
                .data(response).build();
    }

    @PostMapping("")
    public ResponseData<ReturnRequestResponse> createReturnRequest(@RequestBody ReturnRequestRequest request) {
        ReturnRequestResponse response = returnRequestServices.createReturnRequest(request);
        return ResponseData.<ReturnRequestResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Một yêu cầu trả lại mới đã tạo ra thành công!")
                .data(response).build();
    }

    @PutMapping("/{id}")
    public ResponseData<Void> updateReturnRequest(@PathVariable Long id, @RequestBody ReturnRequestRequest request) {
        returnRequestServices.updateReturnRequest(id, request);
        return ResponseData.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Yêu cầu trả hàng với id: " + id + " cập nhật thành công!")
                .build();
    }

    @PostMapping("/time-line")
    public ResponseData<Void> createReturnRequestTimeLine(@RequestBody ReturnTimeLineRequest request) {
        returnRequestServices.createReturnTimeLine(request);
        return ResponseData.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Tạo ReturnTimeLine của ID yêu cầu trả lại: " + request.getReturnRequestId() + " thành công!")
                .build();
    }

    @PostMapping("/time-lines")
    public ResponseData<Void> createReturnRequestTimeLines(@RequestBody List<ReturnTimeLineRequest> requests) {
        returnRequestServices.createReturnTimeLines(requests);
        return ResponseData.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Tạo tất cả ReturnTimeLines của ID yêu cầu trả lại: " + requests.getFirst().getReturnRequestId() + " thành công!")
                .build();
    }

    @GetMapping("/time-line/{returnRequestId}")
    public ResponseData<List<ReturnTimeLineResponse>> getAllReturnTimeLineByReturnRequestId(@PathVariable Long returnRequestId) {
        List<ReturnTimeLineResponse> response = returnRequestServices.getReturnTimeLineByRequestId(returnRequestId);
        return ResponseData.<List<ReturnTimeLineResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Nhận tất cả các mốc thời gian của Yêu cầu trả lại có Id: "+ returnRequestId+" thành công!")
                .data(response).build();
    }
}
