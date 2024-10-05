package org.mufasadev.ecommerce.project.service;

import org.mufasadev.ecommerce.project.models.User;
import org.mufasadev.ecommerce.project.payload.AddressDTO;

import java.util.List;

public interface AddressService {
    AddressDTO addAddress(AddressDTO address, User user);

    List<AddressDTO> getAllAdresses();

    AddressDTO getAddressById(Long addressId);

    List<AddressDTO> getUserAddresses(User user);

    AddressDTO updateAddress(User user,Long addressId, AddressDTO addressDTO);

    String deleteAddress(User user, Long addressId);
}