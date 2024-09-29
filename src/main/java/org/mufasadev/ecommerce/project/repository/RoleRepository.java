package org.mufasadev.ecommerce.project.repository;

import org.mufasadev.ecommerce.project.models.AppRole;
import org.mufasadev.ecommerce.project.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(AppRole appRole);
}