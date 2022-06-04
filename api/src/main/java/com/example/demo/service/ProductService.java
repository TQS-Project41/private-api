package com.example.demo.service;

import java.util.Optional;

import com.example.demo.models.Product;
import com.example.demo.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class ProductService {

  @Autowired
  private ProductRepository repository;

  public Product save(Product product) {
    return repository.save(product);
  }

  public Optional<Product> getById(long id) {
    return repository.findById(id);
  }

  public Page<Product> getAll(Long categoryId, String query, Float minPrice, Float maxPrice, Pageable pageable) {
    Page<Product> page;

    if (categoryId == null && maxPrice == null)
      page = repository.findByNameContainsAndPriceGreaterThan(query, minPrice, pageable);
    else if (categoryId == null)
      page = repository.findByNameContainsAndPriceBetween(query, minPrice, maxPrice, pageable);
    else if (maxPrice == null)
      page = repository.findByCategoryIdAndNameContainsAndPriceGreaterThan(categoryId, query, minPrice, pageable);
    else
      page = repository.findByCategoryIdAndNameContainsAndPriceBetween(categoryId, query, minPrice, maxPrice, pageable);

    return page;
  }

}
