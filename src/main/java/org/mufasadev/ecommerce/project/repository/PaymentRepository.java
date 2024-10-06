package org.mufasadev.ecommerce.project.repository;

import org.mufasadev.ecommerce.project.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long>{

}