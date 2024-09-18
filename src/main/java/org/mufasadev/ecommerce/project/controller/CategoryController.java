package org.mufasadev.ecommerce.project.controller;

import jakarta.validation.Valid;
import org.mufasadev.ecommerce.project.models.Category;
import org.mufasadev.ecommerce.project.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/public/categories")
    public ResponseEntity<List<Category>> getCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @PostMapping("/admin/categories")
    public ResponseEntity<String> addCategory(@Valid @RequestBody Category category) {
        categoryService.createCategory(category);
        return new ResponseEntity<>("Category added successfully", HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable long categoryId) {
        String status = categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @PutMapping("/admin/categories/{categoryId}")
    public ResponseEntity<String> updateCategory(@Valid @PathVariable long categoryId, @RequestBody Category category) {

        categoryService.updateCategory(category,categoryId);
        return new ResponseEntity<>("Category with categoryId: " + categoryId +  " updated successfully", HttpStatus.OK);

    }


}
