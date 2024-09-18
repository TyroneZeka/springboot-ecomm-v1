package org.mufasadev.ecommerce.project.service;

import lombok.RequiredArgsConstructor;
import org.mufasadev.ecommerce.project.exceptions.APIException;
import org.mufasadev.ecommerce.project.exceptions.ResourceNotFoundException;
import org.mufasadev.ecommerce.project.models.Category;
import org.mufasadev.ecommerce.project.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;


    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty())
            throw new APIException("Categories is empty!");
        return categories;
    }

    @Override
    public void createCategory(Category category) {
        Category savedCategory = categoryRepository.findByCategoryName(category.getCategoryName());
        if (savedCategory != null) {
            throw new APIException("Category with name " + category.getCategoryName() + " already exists");
        }
        categoryRepository.save(category);
    }

    @Override
    public void updateCategory(Category category, Long categoryId) {
        Category savedCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));
        category.setCategoryId(categoryId);
        savedCategory = categoryRepository.save(category);
    }


    @Override
    public String deleteCategory(long categoryId) {
        Category savedCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));
        categoryRepository.delete(savedCategory);
        return "Category with categoryId " + categoryId + " deleted!";

    }
}
