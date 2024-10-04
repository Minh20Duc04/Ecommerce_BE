package com.Ecommerce_BE.Service;

import com.Ecommerce_BE.Dto.CategoryDto;
import com.Ecommerce_BE.Dto.Response;

public interface CategoryService {

    Response createCategory(CategoryDto categoryDto);

    Response updateCategory(Long id, CategoryDto categoryDto);

    Response getAllCategories();

    Response getCategoryById(Long categoryId);

    Response deleteCategory(Long categoryId);


}
