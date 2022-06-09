package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.models.Address;
import com.example.demo.models.User;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.UserAddressRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AddressService {

  @Autowired
  private AddressRepository repository;

  @Autowired
  private UserAddressRepository userAddressRepository;

  public Address save(Address address) {
    return repository.save(address);
  }

  public Address getById(long id) {
    Optional<Address> address = repository.findById(id);
    return address.isPresent() ? address.get() : null;
  }

  public List<Address> getAllByUser(User user) {
    return null;
  }

  public List<Address> createUserAddress(User user, Address address) {
    return null;
  }
  
}
