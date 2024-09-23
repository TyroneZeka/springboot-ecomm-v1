package org.mufasadev.ecommerce.project.repository;

import org.mufasadev.ecommerce.project.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
