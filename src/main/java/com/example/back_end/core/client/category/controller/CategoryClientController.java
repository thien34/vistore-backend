package com.example.back_end.core.client.category.controller;

import com.example.back_end.core.client.category.payload.response.CategoryResponse;
import com.example.back_end.core.common.ResponseData;
import com.example.back_end.service.category.CategoryClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/client/categories")
public class CategoryClientController {

    private final CategoryClientService categoryClientService;

    @GetMapping()
    public ResponseData<List<CategoryResponse>> getRootCategories() {

        List<CategoryResponse> response = categoryClientService.getRootCategories();

        return ResponseData.<List<CategoryResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Get root categories success")
                .data(response)
                .build();
    }

}
