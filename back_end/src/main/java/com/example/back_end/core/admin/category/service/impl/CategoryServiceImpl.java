package com.example.back_end.core.admin.category.service.impl;

import com.example.back_end.core.admin.category.mapper.CategoryMapper;
import com.example.back_end.core.admin.category.payload.request.CategoryRequest;
import com.example.back_end.core.admin.category.payload.response.CategoriesResponse;
import com.example.back_end.core.admin.category.payload.response.CategoryNameResponse;
import com.example.back_end.core.admin.category.payload.response.CategoryResponse;
import com.example.back_end.core.admin.category.service.CategoryService;
import com.example.back_end.core.common.PageResponse;
import com.example.back_end.entity.Category;
import com.example.back_end.infrastructure.constant.SortType;
import com.example.back_end.infrastructure.exception.ResourceNotFoundException;
import com.example.back_end.infrastructure.utils.PageUtils;
import com.example.back_end.repository.CategoryRepository;
import com.example.back_end.repository.PictureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final PictureRepository pictureRepository;

    @Override
    @Transactional
    public void createCategory(CategoryRequest categoryRequest) {

        validateCategoryParent(categoryRequest.getCategoryParentId());
        validatePicture(categoryRequest.getPictureId());

        Category category = categoryMapper.mapToCategory(categoryRequest);
        categoryRepository.save(category);
    }

    @Override
    @Transactional
    public void updateCategory(Long id, CategoryRequest request) {

        Category category = findCategoryById(id);

        validateCategoryParent(request.getCategoryParentId());
        validatePicture(request.getPictureId());

        categoryMapper.updateCategoryFromRequest(request, category);
        categoryRepository.save(category);
    }

    @Override
    public PageResponse<List<CategoriesResponse>> getAll(
            String name,
            Boolean published,
            Integer pageNo,
            Integer pageSize) {

        Pageable pageable = PageUtils.createPageable(pageNo, pageSize, "id", SortType.DESC.getValue());
        Page<Category> categoryPage = categoryRepository
                .findAll(CategorySpecification.filterByNameAndPublished(name, published), pageable);

        List<CategoriesResponse> categoriesResponses = categoryMapper
                .toCategoriesResponseList(categoryPage.getContent());

        return PageResponse.<List<CategoriesResponse>>builder()
                .page(categoryPage.getNumber())
                .size(categoryPage.getSize())
                .totalPage(categoryPage.getTotalPages())
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
        Map<Long, CategoryNameResponse> categoryMap = new HashMap<>();

        for (Category category : categories) {
            CategoryNameResponse dto = CategoryNameResponse.toTreeDto(category);
            categoryMap.put(category.getId(), dto);
        }
        List<CategoryNameResponse> roots = new ArrayList<>();

        for (Category category : categories) {
            Long parentId = category.getCategoryParent() != null ? category.getCategoryParent().getId() : null;
            if (parentId == null) {
                roots.add(categoryMap.get(category.getId()));
            } else {
                CategoryNameResponse parent = categoryMap.get(parentId);
                if (parent != null) {
                    parent.getChildren().add(categoryMap.get(category.getId()));
                }
            }
        }
        return roots;
    }

    private void validateCategoryParent(Long categoryParentId) {
        if (categoryParentId != null && !categoryRepository.existsById(categoryParentId)) {
            throw new ResourceNotFoundException("Category parent with id not found: " + categoryParentId);
        }
    }

    private void validatePicture(Long pictureId) {
        if (pictureId != null && !pictureRepository.existsById(pictureId)) {
            throw new ResourceNotFoundException("Picture with id not found: " + pictureId);
        }
    }

    private Category findCategoryById(Long idCategory) {
        return categoryRepository.findById(idCategory)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id not found: " + idCategory));
    }

}
