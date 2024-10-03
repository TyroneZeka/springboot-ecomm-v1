package org.mufasadev.ecommerce.project.repository;

import org.mufasadev.ecommerce.project.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}