package com.example.shopapp.services;

import com.example.shopapp.dto.CategoryDTO;
import com.example.shopapp.models.Category;

import java.util.List;

public interface ICategoryService {

    Category createCategory(CategoryDTO categoryDTO);

    Category getCategoryById(Long id);

    List<Category> getAllCategories();

    Category updateCategory(Long categoryId, CategoryDTO categoryDTO);

    void deleteCategory(Long id);
}
