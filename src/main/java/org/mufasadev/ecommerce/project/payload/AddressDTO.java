package org.mufasadev.ecommerce.project.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {

    private String streetName;
    private String buildingName;
    private String city;
    private String state;
    private String zipCode;
    private String country;
}