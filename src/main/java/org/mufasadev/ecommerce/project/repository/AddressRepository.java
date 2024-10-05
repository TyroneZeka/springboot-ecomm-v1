package org.mufasadev.ecommerce.project.repository;

import org.mufasadev.ecommerce.project.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Long> {
}