package com.example.back_end.core.admin.category.service.impl;

import com.example.back_end.core.admin.category.mapper.CategoryMapper;
import com.example.back_end.core.admin.category.payload.request.CategoryCreationRequest;
import com.example.back_end.core.admin.category.payload.request.CategoryUpdateRequest;
import com.example.back_end.core.admin.category.payload.response.CategoryResponse;
import com.example.back_end.core.admin.category.service.CategoryService;
import com.example.back_end.core.common.PageResponse;
import com.example.back_end.entity.Category;
import com.example.back_end.infrastructure.exception.ResourceNotFoundException;
import com.example.back_end.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public void createCategory(CategoryCreationRequest request) {
        Category category = categoryMapper.mapToCategory(request);
        categoryRepository.save(category);
    }

    @Override
    public void updateCategory(Long id, CategoryUpdateRequest request) {
        Category category = categoryRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id not found: " + id));

        categoryMapper.updateCategoryFromRequest(request, category);
        categoryRepository.save(category);
    }

    @Override
    public PageResponse<?> getAll(String name, Boolean published, int pageNo, int pageSize) {
        if (pageNo < 0 || pageSize <= 0) {
            throw new IllegalArgumentException("Invalid page number or page size");
        }
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("id").descending());

        Page<Category> categoryPage = categoryRepository
                .findByNameContainingAndAndPublished(name, published, pageable);

        List<CategoryResponse> categoryResponses = categoryPage.stream()
                .map(categoryMapper::toDto)
                .sorted(Comparator.comparing(CategoryResponse::getId).reversed())
                .toList();

        return PageResponse.builder()
                .page(categoryPage.getNumber())
                .size(categoryPage.getSize())
                .total(categoryPage.getTotalPages())
                .items(categoryResponses)
                .build();
    }

    @Override
    public CategoryResponse getCategory(Long id) {
        Category category = categoryRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id not found: " + id));

        return categoryMapper.toDto(category);
    }

    @Override
    public void deleteCategories(List<Long> ids) {
        List<Category> categories = categoryRepository.findAllById(ids);
    }
}
