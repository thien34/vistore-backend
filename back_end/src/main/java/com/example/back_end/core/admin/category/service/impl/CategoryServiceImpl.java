package com.example.back_end.core.admin.category.service.impl;

import com.example.back_end.core.admin.category.mapper.CategoryMapper;
import com.example.back_end.core.admin.category.payload.request.CategoryRequest;
import com.example.back_end.core.admin.category.payload.response.CategoriesResponse;
import com.example.back_end.core.admin.category.payload.response.CategoryNameResponse;
import com.example.back_end.core.admin.category.payload.response.CategoryResponse;
import com.example.back_end.core.admin.category.service.CategoryService;
import com.example.back_end.core.common.PageResponse;
import com.example.back_end.entity.Category;
import com.example.back_end.infrastructure.exception.ResourceNotFoundException;
import com.example.back_end.repository.CategoryRepository;
import com.example.back_end.repository.PictureRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final PictureRepository pictureRepository;

    @Transactional
    @Override
    public void createCategory(CategoryRequest categoryRequest) {
        validateCategoryParent(categoryRequest.getCategoryParentId());
        validatePicture(categoryRequest.getPictureId());

        Category category = categoryMapper.mapToCategory(categoryRequest);
        categoryRepository.save(category);
    }

    @Override
    @Transactional
    public void updateCategory(Long id, CategoryRequest request) {
        Category category = categoryRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id not found: " + id));

        validateCategoryParent(request.getCategoryParentId());
        validatePicture(request.getPictureId());

        categoryMapper.updateCategoryFromRequest(request, category);
        categoryRepository.save(category);
    }

    @Override
    public PageResponse<?> getAll(String name, Boolean published, Integer pageNo, Integer pageSize) {
        if (pageNo - 1 < 0 || pageSize <= 0) {
            throw new IllegalArgumentException("Invalid page number or page size");
        }

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by("id").descending());
        Page<Category> categoryPage = categoryRepository.
                findAll(CategorySpecification.filterByNameAndPublished(name, published), pageable);

        List<CategoriesResponse> categoriesResponses = categoryPage.getContent()
                .stream()
                .map(categoryMapper::toCategoriesResponse)
                .toList();

        return PageResponse.builder()
                .page(categoryPage.getNumber())
                .size(categoryPage.getSize())
                .totalPage(categoryPage.getTotalPages())
                .items(categoriesResponses)
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
    @Transactional
    public void deleteCategories(List<Long> ids) {
        List<Category> categories = categoryRepository.findAllById(ids);

        if (categories.size() != ids.size()) {
            throw new ResourceNotFoundException("One or more categories not found for the given ids");
        }

        categoryRepository.deleteAll(categories);
    }

    @Override
    public List<CategoryNameResponse> getCategoriesName() {
        return categoryRepository.findAllCategoriesName();
    }

    public void validateCategoryParent(Long categoryParentId) {
        if (categoryParentId != null && !categoryRepository.existsById(categoryParentId)) {
            throw new ResourceNotFoundException("Category parent with id not found: " + categoryParentId);
        }
    }

    public void validatePicture(Long pictureId) {
        if (pictureId != null && !pictureRepository.existsById(pictureId)) {
            throw new ResourceNotFoundException("Picture with id not found: " + pictureId);
        }
    }
}
