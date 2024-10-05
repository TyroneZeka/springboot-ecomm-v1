package org.mufasadev.ecommerce.project.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.mufasadev.ecommerce.project.exceptions.ResourceNotFoundException;
import org.mufasadev.ecommerce.project.models.Address;
import org.mufasadev.ecommerce.project.models.User;
import org.mufasadev.ecommerce.project.payload.AddressDTO;
import org.mufasadev.ecommerce.project.repository.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;

    @Override
    public AddressDTO addAddress(AddressDTO addressDTO, User user) {
        Address address = modelMapper.map(addressDTO, Address.class);
        List<Address> addresses = user.getAddresses();
        addresses.add(address);
        user.setAddresses(addresses);
        address.setUser(user);
        Address savedAdrress = addressRepository.save(address);
        return modelMapper.map(savedAdrress, AddressDTO.class);
    }

    @Override
    public List<AddressDTO> getAllAdresses() {
        List<Address> addresses = addressRepository.findAll();
        return addresses.stream().map(p -> modelMapper.map(p, AddressDTO.class)).toList();
    }

    @Override
    public AddressDTO getAddressById(Long addressId) {
        Address address = addressRepository.findById(addressId).orElseThrow(
                () -> new ResourceNotFoundException("Address", "id", addressId)
        );
        return modelMapper.map(address, AddressDTO.class);
    }

    @Override
    public List<AddressDTO> getUserAddresses(User user) {
        List<Address> addresses = user.getAddresses();
        return addresses.stream().map(p -> modelMapper.map(p, AddressDTO.class)).toList();
    }

    @Override
    @Transactional
    public AddressDTO updateAddress(User user,Long addressId, AddressDTO addressDTO) {
        List<Address> addresses = user.getAddresses();
        for (Address address : addresses) {
            if(address.getAddressId().equals(addressId)){
                Address address1 = modelMapper.map(addressDTO, Address.class);
                address1.setAddressId(address.getAddressId());
                address1.setUser(user);
                Address savedAddress = addressRepository.save(address1);
                return modelMapper.map(savedAddress, AddressDTO.class);
            }
        }
        throw new ResourceNotFoundException("Address", "id", addressId);
    }

    @Override
    public String deleteAddress(User user, Long addressId) {
        List<Address> addresses = user.getAddresses();
        for (Address address : addresses) {
            if(address.getAddressId().equals(addressId)){
                user.getAddresses().remove(address);
                addressRepository.delete(address);
            }
            else throw new ResourceNotFoundException("Address", "id", addressId);
        }
        return "Address Deleted Successfully";
    }
}