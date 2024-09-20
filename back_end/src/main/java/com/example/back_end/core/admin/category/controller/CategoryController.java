package com.example.back_end.core.admin.category.controller;

import com.example.back_end.core.admin.category.payload.request.CategoryRequest;
import com.example.back_end.core.admin.category.payload.response.CategoriesResponse;
import com.example.back_end.core.admin.category.payload.response.CategoryNameResponse;
import com.example.back_end.core.admin.category.payload.response.CategoryResponse;
import com.example.back_end.core.admin.category.service.CategoryService;
import com.example.back_end.core.common.PageResponse;
import com.example.back_end.core.common.ResponseData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping("/admin/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping()
    public ResponseData<PageResponse<List<CategoriesResponse>>> getAllCategories(
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "published", required = false) Boolean published,
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo, 
            @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize) {

        PageResponse<List<CategoriesResponse>> response = categoryService
                .getAll(name, published, pageNo, pageSize);

        return ResponseData.<PageResponse<List<CategoriesResponse>>>builder()
                .status(HttpStatus.OK.value())
                .message("Get categories success")
                .data(response)
                .build();
    }

    @GetMapping("/list-name")
    public ResponseData<List<CategoryNameResponse>> getAllNameCategories() {

        List<CategoryNameResponse> response = categoryService.getCategoriesName();

        return ResponseData.<List<CategoryNameResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Get categories name success")
                .data(response)
                .build();
    }

    @GetMapping("/{id}")
    public ResponseData<CategoryResponse> getByIdCategory(@PathVariable Long id) {

        CategoryResponse response = categoryService.getCategory(id);

        return ResponseData.<CategoryResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Get category success")
                .data(response)
                .build();
    }

    @PostMapping
    public ResponseData<Void> createCategory(@RequestBody @Valid CategoryRequest categoryRequest) {

        categoryService.createCategory(categoryRequest);

        return ResponseData.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Add product tag success")
                .build();
    }

    @PutMapping("/{id}")
    public ResponseData<Void> updateCategoryById(@PathVariable Long id, @RequestBody @Valid CategoryRequest request) {

        categoryService.updateCategory(id, request);

        return ResponseData.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Update category success")
                .build();
    }

    @DeleteMapping
    public ResponseData<Void> deleteAllByCategoryIds(@RequestBody List<Long> ids) {

        categoryService.deleteCategories(ids);

        return ResponseData.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Delete categories success")
                .build();
    }

}
