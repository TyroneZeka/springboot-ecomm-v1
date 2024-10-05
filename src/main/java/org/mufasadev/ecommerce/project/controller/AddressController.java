package org.mufasadev.ecommerce.project.controller;

import lombok.RequiredArgsConstructor;
import org.mufasadev.ecommerce.project.models.Address;
import org.mufasadev.ecommerce.project.models.User;
import org.mufasadev.ecommerce.project.payload.AddressDTO;
import org.mufasadev.ecommerce.project.service.AddressService;
import org.mufasadev.ecommerce.project.utils.AuthUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AddressController {

    private final AuthUtils authUtils;
    private final AddressService addressService;

    @PostMapping("/address/user/add")
    public ResponseEntity<AddressDTO> addAddress(@RequestBody AddressDTO addressDTO) {
        User user = authUtils.loggedInUser();
        AddressDTO addressDTO1 = addressService.addAddress(addressDTO,user);
        return new ResponseEntity<>(addressDTO1, HttpStatus.CREATED);
    }

    @GetMapping("/address")
    public ResponseEntity<List<AddressDTO>> getAllAddresses() {
        List<AddressDTO> addresses = addressService.getAllAdresses();
        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    @GetMapping("/address/{addressId}")
    public ResponseEntity<AddressDTO> getAddressById(@PathVariable Long addressId) {
        AddressDTO addressDTO = addressService.getAddressById(addressId);
        return new ResponseEntity<>(addressDTO, HttpStatus.OK);
    }

    @GetMapping("/address/user")
    public ResponseEntity<List<AddressDTO>> getAllUserAddresses() {
        User user = authUtils.loggedInUser();
        List<AddressDTO> addressDTOS = addressService.getUserAddresses(user);
        return new ResponseEntity<>(addressDTOS, HttpStatus.OK);
    }

    @PutMapping("/address/user/{addressId}")
    public ResponseEntity<AddressDTO> updateAddress(@RequestBody AddressDTO addressDTO, @PathVariable Long addressId) {
        User user = authUtils.loggedInUser();
        AddressDTO addressDTO1 = addressService.updateAddress(user,addressId,addressDTO);
        return new ResponseEntity<>(addressDTO1, HttpStatus.OK);
    }

    @DeleteMapping("/address/user/{addressId}")
    public ResponseEntity<String> deleteAddress(@PathVariable Long addressId) {
        User user = authUtils.loggedInUser();
        String status = addressService.deleteAddress(user,addressId);
        return new ResponseEntity<>(status,HttpStatus.OK);
    }
}