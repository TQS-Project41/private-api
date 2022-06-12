package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.models.Store;
import com.example.demo.repository.StoreRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class StoreService {

  @Autowired
  private StoreRepository repository;

  public Store save(Store store) {
    return repository.save(store);
  }

  public Store getById(long id) {
    Optional<Store> store = repository.findById(id);
    return store.isPresent() ? store.get() : null;
  }

  public List<Store> getAll() {
    return repository.findAll();
  }

}
