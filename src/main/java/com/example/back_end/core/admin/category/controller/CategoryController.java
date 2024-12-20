package com.example.back_end.core.admin.category.controller;

import com.example.back_end.core.admin.category.payload.request.CategoryRequest;
import com.example.back_end.core.admin.category.payload.request.CategorySearchRequest;
import com.example.back_end.core.admin.category.payload.response.CategoryNameResponse;
import com.example.back_end.core.admin.category.payload.response.CategoryResponse;
import com.example.back_end.core.common.PageResponse1;
import com.example.back_end.core.common.ResponseData;
import com.example.back_end.service.category.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/admin/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping()
    public ResponseData<PageResponse1<List<CategoryResponse>>> getAllCategories(
            @ParameterObject CategorySearchRequest searchRequest) {

        PageResponse1<List<CategoryResponse>> response = categoryService.getAll(searchRequest);

        return ResponseData.<PageResponse1<List<CategoryResponse>>>builder()
                .status(HttpStatus.OK.value())
                .message("Nhận thành công danh mục")
                .data(response)
                .build();
    }

    @GetMapping("/list-name")
    public ResponseData<List<CategoryNameResponse>> getAllNameCategories() {

        List<CategoryNameResponse> response = categoryService.getCategoriesName();

        return ResponseData.<List<CategoryNameResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Nhận thành công tên danh mục")
                .data(response)
                .build();
    }

    @GetMapping("/{id}")
    public ResponseData<CategoryResponse> getByIdCategory(@PathVariable Long id) {

        CategoryResponse response = categoryService.getCategory(id);

        return ResponseData.<CategoryResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Nhận thành công danh mục")
                .data(response)
                .build();
    }

    @PostMapping
    public ResponseData<Void> createCategory(@RequestBody @Valid CategoryRequest categoryRequest) {

        categoryService.createCategory(categoryRequest);

        return ResponseData.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Thêm thẻ sản phẩm thành công")
                .build();
    }

    @PutMapping("/{id}")
    public ResponseData<Void> updateCategoryById(@PathVariable Long id, @RequestBody @Valid CategoryRequest request) {

        categoryService.updateCategory(id, request);

        return ResponseData.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Cập nhật danh mục thành công")
                .build();
    }

    @DeleteMapping
    public ResponseData<Void> deleteAllByCategoryIds(@RequestBody List<Long> ids) {

        categoryService.deleteCategories(ids);

        return ResponseData.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Xóa danh mục thành công")
                .build();
    }

}
