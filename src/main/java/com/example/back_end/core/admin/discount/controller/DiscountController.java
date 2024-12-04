package com.example.back_end.core.admin.discount.controller;

import com.example.back_end.core.admin.discount.payload.request.DiscountFilterRequest;
import com.example.back_end.core.admin.discount.payload.request.DiscountRequest;
import com.example.back_end.core.admin.discount.payload.response.DiscountFullResponse;
import com.example.back_end.core.admin.discount.payload.response.DiscountNameResponse;
import com.example.back_end.core.admin.discount.payload.response.DiscountResponse;
import com.example.back_end.core.common.ResponseData;
import com.example.back_end.service.discount.DiscountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
@RequestMapping("/admin/discounts")
public class DiscountController {

    private final DiscountService discountService;

    @GetMapping("/list-name")
    public ResponseData<List<DiscountNameResponse>> getAllDiscountNames(
            @RequestParam(name = "discountType") Integer discountType) {

        List<DiscountNameResponse> responses = discountService.getAllDiscounts(discountType);

        return ResponseData.<List<DiscountNameResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Nhận thành công tên danh mục")
                .data(responses)
                .build();
    }

    @GetMapping
    public ResponseData<List<DiscountResponse>> getAllDiscounts(
            @ModelAttribute DiscountFilterRequest filterRequest) {

        List<DiscountResponse> response = discountService.getAllDiscounts(filterRequest);

        return ResponseData.<List<DiscountResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Nhận tất cả giảm giá thành công")
                .data(response)
                .build();
    }

    @PostMapping
    @Operation(summary = "Tạo giảm giá mới", description = "Tạo giảm giá mới với các chi tiết được cung cấp.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Giảm giá được tạo thành công"),
            @ApiResponse(responseCode = "400", description = "Tải trọng yêu cầu không hợp lệ")
    })
    public ResponseData<Void> createDiscount(@Valid @RequestBody DiscountRequest discountRequest) {
        discountService.createDiscount(discountRequest);

        return ResponseData.<Void>builder()
                .status(HttpStatus.CREATED.value())
                .message("Giảm giá được tạo thành công")
                .data(null)
                .build();
    }

    @GetMapping("/by-type/{type}")
    public ResponseData<List<DiscountResponse>> getDiscountsByType(@PathVariable Integer type) {
        List<DiscountResponse> responses = discountService.getDiscountsByType(type);

        return ResponseData.<List<DiscountResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Giảm giá được truy xuất thành công")
                .data(responses)
                .build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Cập nhật giảm giá theo ID", description = "Cập nhật ưu đãi giảm giá hiện có bằng ID được cung cấp.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cập nhật giảm giá thành công"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy giảm giá")
    })
    public ResponseData<Void> updateDiscount(
            @PathVariable Long id,
            @Valid @RequestBody DiscountRequest discountRequest) {

        discountService.updateDiscount(id, discountRequest);

        return ResponseData.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Cập nhật giảm giá thành công")
                .data(null)
                .build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Xóa giảm giá theo ID", description = "Xóa giảm giá hiện có bằng ID được cung cấp.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Giảm giá đã được xóa thành công"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy giảm giá")
    })
    public ResponseData<Void> deleteDiscount(@PathVariable Long id) {
        discountService.deleteDiscount(id);

        return ResponseData.<Void>builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message("Giảm giá đã được xóa thành công")
                .build();
    }

    @PutMapping("/{id}/end-date-now")
    @Operation(summary = "Cập nhật endDateUtc thành thời điểm hiện tại", description = "Cập nhật endDateUtc của ưu đãi giảm giá thành ngày và giờ hiện tại.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ngày kết thúc được cập nhật thành công"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy giảm giá")
    })
    public ResponseData<Void> updateEndDateToNow(@PathVariable Long id) {
        discountService.updateEndDateToNow(id);

        return ResponseData.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Ngày kết thúc được cập nhật thành thời gian hiện tại thành công")
                .data(null)
                .build();
    }

    @PutMapping("/{id}/cancel")
    @Operation(summary = "Hủy giảm giá theo ID", description = "Đặt trạng thái giảm giá thành 'CANCEL' bằng ID được cung cấp")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Discount status set to 'CANCEL' successfully"),
            @ApiResponse(responseCode = "404", description = "Discount not found")
    })
    public ResponseData<Void> cancelDiscount(@PathVariable Long id) {
        discountService.cancelDiscount(id);

        return ResponseData.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Trạng thái giảm giá được đặt thành 'CANCEL' thành công")
                .data(null)
                .build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Nhận giảm giá theo ID", description = "Truy xuất chi tiết giảm giá bằng ID được cung cấp.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Đã tìm thấy giảm giá thành công"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy giảm giá")
    })
    public ResponseData<DiscountFullResponse> getDiscountById(@PathVariable Long id) {
        DiscountFullResponse discountResponse = discountService.getDiscountById(id);

        return ResponseData.<DiscountFullResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Giảm giá được lấy thành công")
                .data(discountResponse)
                .build();
    }

    @GetMapping("/by-product/{productId}")
    @Operation(summary = "Nhận giảm giá áp dụng cho một sản phẩm cụ thể",
            description = "Truy xuất tất cả các khoản giảm giá áp dụng cho một sản phẩm bằng ID sản phẩm được cung cấp.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Giảm giá được truy xuất thành công"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy sản phẩm hoặc không áp dụng giảm giá cho sản phẩm này")
    })
    public ResponseData<List<DiscountResponse>> getDiscountsByProductId(@PathVariable Long productId) {
        List<DiscountResponse> responses = discountService.getDiscountsByProductId(productId);

        return ResponseData.<List<DiscountResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Giảm giá áp dụng cho sản phẩm được truy xuất thành công")
                .data(responses)
                .build();
    }

}
