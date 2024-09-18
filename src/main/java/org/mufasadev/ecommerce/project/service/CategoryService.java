package org.mufasadev.ecommerce.project.service;

import org.mufasadev.ecommerce.project.models.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();

    void createCategory(Category category);

    String deleteCategory(long categoryId);

    void updateCategory(Category category, Long categoryId);
}
