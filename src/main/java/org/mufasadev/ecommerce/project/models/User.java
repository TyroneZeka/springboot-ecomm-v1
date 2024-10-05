package org.mufasadev.ecommerce.project.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "_users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")
})
public class User {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @NotBlank
    @Size(min = 3, max = 25)
    private String username;
    @NotBlank
    @Size(min = 8, max = 120)
    private String password;
    @NotBlank
    @Email
    private String email;

    @Getter
    @Setter
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "user_role",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @Getter
    @Setter
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE},orphanRemoval = true,mappedBy = "user")
//    @JoinTable(name = "user_address",
//                joinColumns = @JoinColumn(name = "user_id"),inverseJoinColumns = @JoinColumn(name = "address_id"))
    private List<Address> addresses = new ArrayList<>();

    @ToString.Exclude
    @OneToOne(mappedBy = "user",cascade = {CascadeType.PERSIST, CascadeType.MERGE},orphanRemoval = true)
    private Cart cart;


    @ToString.Exclude
    @Getter
    @Setter
    @OneToMany(mappedBy = "user",cascade = {CascadeType.PERSIST,CascadeType.MERGE}, orphanRemoval = true)
    private Set<Product> products = new HashSet<>();

    public User(String username, String email, String encode) {
        this.username = username;
        this.email = email;
        this.password = encode;
    }
}