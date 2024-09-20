package org.mufasadev.ecommerce.project.service;

import org.mufasadev.ecommerce.project.models.Category;
import org.mufasadev.ecommerce.project.payload.CategoryDTO;
import org.mufasadev.ecommerce.project.payload.CategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    CategoryDTO createCategory(CategoryDTO category);

    CategoryDTO deleteCategory(long categoryId);

    CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId);
}
