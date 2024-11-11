package com.example.back_end.service.category.impl;

import com.example.back_end.core.admin.category.mapper.CategoryMapper;
import com.example.back_end.core.admin.category.payload.request.CategoryRequest;
import com.example.back_end.core.admin.category.payload.request.CategorySearchRequest;
import com.example.back_end.core.admin.category.payload.response.CategoryNameResponse;
import com.example.back_end.core.admin.category.payload.response.CategoryResponse;
import com.example.back_end.core.common.PageResponse1;
import com.example.back_end.entity.Category;
import com.example.back_end.infrastructure.exception.ResourceNotFoundException;
import com.example.back_end.infrastructure.utils.PageUtils;
import com.example.back_end.infrastructure.utils.StringUtils;
import com.example.back_end.repository.CategoryRepository;
import com.example.back_end.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public void createCategory(CategoryRequest categoryRequest) {
        validateCategoryNameAndSlug(categoryRequest.getName(), null);
        validateCategoryParent(categoryRequest.getCategoryParentId());

        Category category = categoryMapper.toEntity(categoryRequest);
        String slug = StringUtils.generateSlug(categoryRequest.getName());

        validateSlugUniqueness(slug, null);
        category.setSlug(slug);
        category.setDeleted(false);

        categoryRepository.save(category);
    }

    @Override
    public void updateCategory(Long id, CategoryRequest request) {
        Category category = findCategoryById(id);

        validateCategoryNameAndSlug(request.getName(), category);
        validateCategoryParent(request.getCategoryParentId());
        validateParentNotChild(id, request.getCategoryParentId());

        String newSlug = StringUtils.generateSlug(request.getName());
        validateSlugUniqueness(newSlug, category);

        categoryMapper.updateCategoryFromRequest(request, category);
        category.setSlug(newSlug);

        categoryRepository.save(category);
    }

    @Override
    public PageResponse1<List<CategoryResponse>> getAll(CategorySearchRequest searchRequest) {

        Pageable pageable = PageUtils.createPageable(
                searchRequest.getPageNo(),
                searchRequest.getPageSize(),
                searchRequest.getSortBy(),
                searchRequest.getSortDir());

        Page<Category> categoryPage = categoryRepository
                .findAll(CategorySpecification.filterByName(searchRequest.getName()), pageable);

        List<CategoryResponse> categoriesResponses = categoryMapper
                .toDtos(categoryPage.getContent());

        return PageResponse1.<List<CategoryResponse>>builder()
                .totalItems(categoryPage.getTotalElements())
                .totalPages(categoryPage.getTotalPages())
                .items(categoriesResponses)
                .build();
    }

    @Override
    public CategoryResponse getCategory(Long id) {

        Category category = findCategoryById(id);
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

        List<Category> categories = categoryRepository.findAll();
        List<CategoryNameResponse> roots = new ArrayList<>();

        Map<Long, CategoryNameResponse> categoryMap = categories.stream()
                .collect(Collectors.toMap(Category::getId, categoryMapper::toTreeDto));

        for (Category category : categories) {
            Long parentId = category.getCategoryParent() != null ? category.getCategoryParent().getId() : null;
            CategoryNameResponse currentDto = categoryMap.get(category.getId());

            // check category have parent or not
            if (parentId == null) {
                // if is parented, add to roots
                roots.add(currentDto);
            } else {
                // if is child having parent, add to parent's children
                categoryMap.get(parentId).getChildren().add(currentDto);
            }
        }
        return roots;
    }

    private void validateCategoryParent(Long categoryParentId) {
        if (categoryParentId != null && !categoryRepository.existsById(categoryParentId)) {
            throw new ResourceNotFoundException("Category parent with id not found: " + categoryParentId);
        }
    }

    private Category findCategoryById(Long idCategory) {
        return categoryRepository.findById(idCategory)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id not found: " + idCategory));
    }

    // Private helper method to get all children of a category
    private List<Long> getCategoriesChild(Long id) {
        List<Category> categories = categoryRepository.findAll();
        List<Long> children = new ArrayList<>();
        children.add(id);
        for (Category category : categories) {
            if (category.getCategoryParent() != null && Objects.equals(category.getCategoryParent().getId(), id)) {
                children.addAll(getCategoriesChild(category.getId()));
            }
        }
        return children;
    }

    // Private helper method for name and slug validation
    private void validateCategoryNameAndSlug(String name, Category existingCategory) {
        if (existingCategory == null || !name.equals(existingCategory.getName())) {
            if (categoryRepository.existsByName(name)) {
                throw new IllegalArgumentException("Category name already exists");
            }
        }
    }

    // Private helper method to validate slug uniqueness
    private void validateSlugUniqueness(String slug, Category existingCategory) {
        if (existingCategory == null || !slug.equals(existingCategory.getSlug())) {
            if (categoryRepository.existsBySlug(slug)) {
                throw new IllegalArgumentException("Duplicate slug");
            }
        }
    }

    // Private helper method to ensure the parent is not a child
    private void validateParentNotChild(Long id, Long parentId) {
        getCategoriesChild(id).stream()
                .filter(categoryId -> categoryId.equals(parentId))
                .findFirst()
                .ifPresent(categoryId -> {
                    throw new IllegalArgumentException("Category parent cannot be a child of itself");
                });
    }

}
