package com.Ecommerce_BE.Service.ServiceImp;

import com.Ecommerce_BE.Dto.CategoryDto;
import com.Ecommerce_BE.Dto.Response;
import com.Ecommerce_BE.Exception.NotFoundException;
import com.Ecommerce_BE.Mapper.EntityDtoMapper;
import com.Ecommerce_BE.Model.Category;
import com.Ecommerce_BE.Repository.CategoryRepository;
import com.Ecommerce_BE.Service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor

public class CategoryServiceImp implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final EntityDtoMapper entityDtoMapper;


    @Override
    public Response createCategory(CategoryDto categoryRequest) {
        Category category = new Category();
        category.setName(categoryRequest.getName());
        categoryRepository.save(category);

        return Response.builder()
                .status(200)
                .message("Category created successfully")
                .build();
    }

    @Override
    public Response updateCategory(Long id, CategoryDto categoryRequest) {
        Category category = categoryRepository.findById(id).orElseThrow(()-> new NotFoundException("Category Not Found"));
        category.setName(categoryRequest.getName());
        categoryRepository.save(category);

        return Response.builder()
                .status(200)
                .message("Category updated successfully")
                .build();
    }

    @Override
    public Response getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDto> categoryDtoList = categories.stream()
                .map(entityDtoMapper::mapCategoryToDtoBasic)
                .collect(Collectors.toList());

        return Response.builder()
                .status(200)
                .categoryList(categoryDtoList)
                .build();
    }

    @Override
    public Response getCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new NotFoundException("Category Not Found"));
        CategoryDto categoryDto = entityDtoMapper.mapCategoryToDtoBasic(category);

        return Response.builder()
                .status(200)
                .category(categoryDto)
                .build();
    }

    @Override
    public Response deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new NotFoundException("Category Not Found"));
        categoryRepository.delete(category);

        return Response.builder()
                .status(200)
                .message("Category delete successfully")
                .build();

    }
}
