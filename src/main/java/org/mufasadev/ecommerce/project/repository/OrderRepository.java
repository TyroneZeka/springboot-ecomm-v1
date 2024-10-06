package org.mufasadev.ecommerce.project.repository;

import org.mufasadev.ecommerce.project.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}