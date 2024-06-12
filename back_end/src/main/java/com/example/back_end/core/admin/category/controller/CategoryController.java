package com.example.back_end.core.admin.category.controller;

import com.example.back_end.core.admin.category.payload.request.CategoryCreationRequest;
import com.example.back_end.core.admin.category.payload.request.CategoryUpdateRequest;
import com.example.back_end.core.admin.category.payload.response.CategoryResponse;
import com.example.back_end.core.admin.category.service.CategoryService;
import com.example.back_end.core.common.PageResponse;
import com.example.back_end.core.common.ResponseData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/categories")
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseData<?> getAll(@RequestParam(value = "name", defaultValue = "") String name,
                                  @RequestParam(value = "published", defaultValue = "") Boolean published,
                                  @RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
                                  @RequestParam(value = "pageSize", defaultValue = "6") int pageSize) {
        try {
            PageResponse<?> response = categoryService.getAll(name, published, pageNo, pageSize);

            return ResponseData.builder()
                    .status(HttpStatus.OK.value())
                    .message("Get categories success")
                    .data(response)
                    .build();
        } catch (Exception e) {
            log.error("Error getting categories", e);
            return ResponseData.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message(e.getMessage())
                    .build();
        }
    }

    @GetMapping("/{id}")
    public ResponseData<?> getById(@PathVariable Long id) {
        try {
            CategoryResponse response = categoryService.getCategory(id);
            return ResponseData.builder()
                    .status(HttpStatus.OK.value())
                    .message("Get category success")
                    .data(response)
                    .build();
        } catch (Exception e) {
            log.error("Error getting category", e);
            return ResponseData.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message(e.getMessage())
                    .build();
        }
    }

    @PostMapping
    public ResponseData<?> create(@RequestBody CategoryCreationRequest request) {
        log.info("Request add category, {}", request);
        try {
            categoryService.createCategory(request);
            return ResponseData.builder()
                    .status(HttpStatus.OK.value())
                    .message("Add product tag success")
                    .build();
        } catch (Exception e) {
            log.error("Error adding categories", e);
            return ResponseData.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message(e.getMessage())
                    .build();
        }
    }

    @PutMapping("/{id}")
    public ResponseData<?> update(@PathVariable Long id, @RequestBody CategoryUpdateRequest request) {
        log.info("Request to update category with id: {}, {}", id, request);
        try {
            categoryService.updateCategory(id, request);
            return ResponseData.builder()
                    .status(HttpStatus.OK.value())
                    .message("Update product tag success")
                    .build();
        } catch (Exception e) {
            log.error("Error updating product tag", e);
            return ResponseData.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message(e.getMessage())
                    .build();
        }
    }

    @DeleteMapping
    public ResponseData<?> delete(@RequestBody List<Long> ids) {
        log.info("Request to delete categories with ids: {}", ids);
        try {
            categoryService.deleteCategories(ids);
            return ResponseData.builder()
                    .status(HttpStatus.OK.value())
                    .message("Delete product tags success")
                    .build();
        } catch (Exception e) {
            log.error("Error deleting product tags", e);
            return ResponseData.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message(e.getMessage())
                    .build();
        }
    }
}
