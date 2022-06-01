package com.example.demo.Service;

import java.util.List;
import java.util.Optional;

import com.example.demo.Models.Product;
import com.example.demo.Repository.ProductRepository;

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
