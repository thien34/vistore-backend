package com.example.back_end.core.admin.discount.controller;

import com.example.back_end.core.admin.discount.payload.request.DiscountFilterRequest;
import com.example.back_end.core.admin.discount.payload.request.DiscountRequest;
import com.example.back_end.core.admin.discount.payload.response.DiscountFullResponse;
import com.example.back_end.core.admin.discount.payload.response.DiscountNameResponse;
import com.example.back_end.core.admin.discount.payload.response.DiscountResponse;
import com.example.back_end.core.admin.discount.service.DiscountService;
import com.example.back_end.core.common.PageResponse;
import com.example.back_end.core.common.ResponseData;
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
                .message("Get categories name success")
                .data(responses)
                .build();
    }

    @GetMapping
    public ResponseData<PageResponse<List<DiscountResponse>>> getAllDiscounts(
            @ModelAttribute DiscountFilterRequest filterRequest) {

        PageResponse<List<DiscountResponse>> response = discountService.getAllDiscounts(filterRequest);

        return ResponseData.<PageResponse<List<DiscountResponse>>>builder()
                .status(HttpStatus.OK.value())
                .message("Get all discounts successfully")
                .data(response)
                .build();
    }

    @PostMapping
    @Operation(summary = "Create a new discount", description = "Create a new discount with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Discount created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request payload")
    })
    public ResponseData<Void> createDiscount(@Valid @RequestBody DiscountRequest discountRequest) {
        discountService.createDiscount(discountRequest);

        return ResponseData.<Void>builder()
                .status(HttpStatus.CREATED.value())
                .message("Discount created successfully")
                .data(null)
                .build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update discount by ID", description = "Update an existing discount using the provided ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Discount updated successfully"),
            @ApiResponse(responseCode = "404", description = "Discount not found")
    })
    public ResponseData<Void> updateDiscount(
            @PathVariable Long id,
            @Valid @RequestBody DiscountRequest discountRequest) {

        discountService.updateDiscount(id, discountRequest);

        return ResponseData.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Discount updated successfully")
                .data(null)
                .build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a discount by ID", description = "Delete an existing discount using the provided ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Discount deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Discount not found")
    })
    public ResponseData<Void> deleteDiscount(@PathVariable Long id) {
        discountService.deleteDiscount(id);

        return ResponseData.<Void>builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message("Discount deleted successfully")
                .build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get discount by ID", description = "Retrieve discount details using the provided ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Discount found successfully"),
            @ApiResponse(responseCode = "404", description = "Discount not found")
    })
    public ResponseData<DiscountFullResponse> getDiscountById(@PathVariable Long id) {
        DiscountFullResponse discountResponse = discountService.getDiscountById(id);

        return ResponseData.<DiscountFullResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Discount retrieved successfully")
                .data(discountResponse)
                .build();
    }

}
