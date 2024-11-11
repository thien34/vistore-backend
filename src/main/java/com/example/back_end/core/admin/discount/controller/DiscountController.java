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
                .message("Get categories name success")
                .data(responses)
                .build();
    }

    @GetMapping
    public ResponseData<List<DiscountResponse>> getAllDiscounts(
            @ModelAttribute DiscountFilterRequest filterRequest) {

        List<DiscountResponse> response = discountService.getAllDiscounts(filterRequest);

        return ResponseData.<List<DiscountResponse>>builder()
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

    @GetMapping("/by-type/{type}")
    public ResponseData<List<DiscountResponse>> getDiscountsByType(@PathVariable Integer type) {
        List<DiscountResponse> responses = discountService.getDiscountsByType(type);

        return ResponseData.<List<DiscountResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Discounts retrieved successfully")
                .data(responses)
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
    @PutMapping("/{id}/end-date-now")
    @Operation(summary = "Update endDateUtc to current time", description = "Update the endDateUtc of a discount to the current date and time.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "End date updated successfully"),
            @ApiResponse(responseCode = "404", description = "Discount not found")
    })
    public ResponseData<Void> updateEndDateToNow(@PathVariable Long id) {
        discountService.updateEndDateToNow(id);

        return ResponseData.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("End date updated to current time successfully")
                .data(null)
                .build();
    }
    @PutMapping("/{id}/cancel")
    @Operation(summary = "Cancel discount by ID", description = "Set the status of a discount to 'CANCEL' using the provided ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Discount status set to 'CANCEL' successfully"),
            @ApiResponse(responseCode = "404", description = "Discount not found")
    })
    public ResponseData<Void> cancelDiscount(@PathVariable Long id) {
        discountService.cancelDiscount(id);

        return ResponseData.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Discount status set to 'CANCEL' successfully")
                .data(null)
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
    @GetMapping("/by-product/{productId}")
    @Operation(summary = "Get discounts applied to a specific product", description = "Retrieve all discounts applied to a product using the provided product ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Discounts retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found or no discounts applied to this product")
    })
    public ResponseData<List<DiscountResponse>> getDiscountsByProductId(@PathVariable Long productId) {
        List<DiscountResponse> responses = discountService.getDiscountsByProductId(productId);

        return ResponseData.<List<DiscountResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Discounts applied to product retrieved successfully")
                .data(responses)
                .build();
    }

}
