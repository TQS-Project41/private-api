package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.models.Address;
import com.example.demo.repository.AddressRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AddressService {

  @Autowired
  private AddressRepository repository;

  public Address save(Address address) {
    return repository.save(address);
  }

  public Address getById(long id) {
    Optional<Address> address = repository.findById(id);
    return address.isPresent() ? address.get() : null;
  }

  public List<Address> getAll() {
    /* TODO this list should be filtered by the user authenticated */
    return repository.findAll();
  }
  
}
