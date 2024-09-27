package org.mufasadev.ecommerce.project.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    @NotBlank
    @Size(min = 2, max = 50)
    private String streetName;

    @NotBlank
    @Size(min = 2, max = 50, message = "fds")
    private String buildingName;
    @NotBlank
    @Size(min = 2, max = 50)
    private String city;
    @NotBlank
    @Size(min = 2, max = 50)
    private String state;
    @NotBlank
    private String zipCode;
    @NotBlank
    @Size(min = 5, max = 50)
    private String country;

    public Address(String streetName, String buildingName, String city, String state, String zipCode, String country) {
        this.streetName = streetName;
        this.buildingName = buildingName;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.country = country;
    }

    @ToString.Exclude
    @ManyToMany(mappedBy = "addresses")
    private List<User> users = new ArrayList<>();
}
