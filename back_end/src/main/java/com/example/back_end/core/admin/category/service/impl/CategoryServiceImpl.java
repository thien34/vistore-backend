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
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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

        getCategoriesChild(id).stream()
                .filter(categoryId -> categoryId.equals(request.getCategoryParentId()))
                .findFirst()
                .ifPresent(categoryId -> {
                    throw new IllegalArgumentException("Category parent cannot be child of itself");
                });

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

    private void validatePicture(Long pictureId) {
        if (pictureId != null && !pictureRepository.existsById(pictureId)) {
            throw new ResourceNotFoundException("Picture with id not found: " + pictureId);
        }
    }

    private Category findCategoryById(Long idCategory) {
        return categoryRepository.findById(idCategory)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id not found: " + idCategory));
    }

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

}
