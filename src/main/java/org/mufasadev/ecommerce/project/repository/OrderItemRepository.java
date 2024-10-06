package org.mufasadev.ecommerce.project.repository;

import org.mufasadev.ecommerce.project.models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}