package com.example.back_end.service.category.impl;

import com.example.back_end.core.client.category.mapper.CategoryClientMapper;
import com.example.back_end.core.client.category.payload.response.CategoryResponse;
import com.example.back_end.entity.Category;
import com.example.back_end.repository.CategoryRepository;
import com.example.back_end.service.category.CategoryClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryClientServiceImpl implements CategoryClientService {

    private final CategoryRepository categoryRepository;
    private final CategoryClientMapper categoryClientMapper;

    public List<CategoryResponse> getRootCategories() {

        List<Category> categories = categoryRepository.findByCategoryParentIsNull();
        return categoryClientMapper.toDtos(categories);

    }

}
