package org.mufasadev.ecommerce.project.repository;

import org.mufasadev.ecommerce.project.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}