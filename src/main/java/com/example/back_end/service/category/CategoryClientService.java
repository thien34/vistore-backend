package com.example.back_end.service.category;

import com.example.back_end.core.client.category.payload.response.CategoryResponse;

import java.util.List;

public interface CategoryClientService {

    List<CategoryResponse> getRootCategories();

}
