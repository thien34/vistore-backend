package com.example.back_end.core.admin.category.controller;

import com.example.back_end.core.admin.category.payload.request.CategoryRequest;
import com.example.back_end.core.admin.category.payload.response.CategoryNameResponse;
import com.example.back_end.core.admin.category.payload.response.CategoryResponse;
import com.example.back_end.core.admin.category.service.CategoryService;
import com.example.back_end.core.common.PageResponse;
import com.example.back_end.core.common.ResponseData;
import com.example.back_end.core.common.ResponseError;
import jakarta.validation.Valid;
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
@RequestMapping("/admin/categories")
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping()
    public ResponseData<?> getAll(@RequestParam(value = "name", defaultValue = "") String name,
                                  @RequestParam(value = "published", defaultValue = "") Boolean published,
                                  @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                  @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize) {
        try {
            PageResponse<?> response = categoryService.getAll(name, published, pageNo, pageSize);
            return new ResponseData<>(HttpStatus.OK.value(), "Get categories success", response);
        } catch (Exception e) {
            log.error("Error getting categories", e);
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

    @GetMapping("/name")
    public ResponseData<?> getAllName() {
        try {
            List<CategoryNameResponse> response = categoryService.getCategoriesName();
            return new ResponseData<>(HttpStatus.OK.value(), "Get categories name success", response);
        } catch (Exception e) {
            log.error("Error getting categories name", e);
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseData<?> getById(@PathVariable Long id) {
        try {
            CategoryResponse response = categoryService.getCategory(id);
            return new ResponseData<>(HttpStatus.OK.value(), "Get category success", response);
        } catch (Exception e) {
            log.error("Error getting category", e);
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

    @PostMapping
    public ResponseData<?> create(@RequestBody @Valid CategoryRequest categoryRequest) {
        log.info("Request add category, {}", categoryRequest);
        try {
            categoryService.createCategory(categoryRequest);
            return new ResponseData<>(HttpStatus.OK.value(), "Add product tag success");
        } catch (Exception e) {
            log.error("Error adding categories", e);
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseData<?> update(@PathVariable Long id, @RequestBody @Valid CategoryRequest request) {
        log.info("Request to update category with id: {}, {}", id, request);
        try {
            categoryService.updateCategory(id, request);
            return new ResponseData<>(HttpStatus.OK.value(), "Update product tag success");
        } catch (Exception e) {
            log.error("Error updating product tag", e);
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseData<?> delete(@RequestBody List<Long> ids) {
        log.info("Request to delete categories with ids: {}", ids);
        try {
            categoryService.deleteCategories(ids);
            return new ResponseData<>(HttpStatus.OK.value(), "Delete product tags success");
        } catch (Exception e) {
            log.error("Error deleting product tags", e);
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }
}
