package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.models.Product;
import com.example.demo.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProductService {

  @Autowired
  private ProductRepository repository;

  public Product save(Product product) {
    return repository.save(product);
  }

  public Product getById(long id) {
    Optional<Product> product = repository.findById(id);
    return product.isPresent() ? product.get() : null;
  }

  public List<Product> getAll() {
    return repository.findAll();
  }

}
