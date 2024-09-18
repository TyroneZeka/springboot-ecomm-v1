package org.mufasadev.ecommerce.project.repository;

import org.mufasadev.ecommerce.project.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByCategoryName(String categoryName);
}
