package org.mufasadev.ecommerce.project.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer roleId;

    @Enumerated(EnumType.STRING)
    @ToString.Exclude
    @Column(length = 20, name = "roleName")
    private AppRole roleName;

    public Role(AppRole appRole) {
        this.roleName = appRole;
    }
}