package com.example.demo.repository;

import com.example.demo.models.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

  public Page<Product> findByNameContainsAndPriceGreaterThan(String query, float minPrice, Pageable pageable);

  public Page<Product> findByNameContainsAndPriceBetween(String query, float minPrice, float maxPrice, Pageable pageable);

  public Page<Product> findByCategoryIdAndNameContainsAndPriceGreaterThan(Long categoryId, String query, float minPrice, Pageable pageable);

  public Page<Product> findByCategoryIdAndNameContainsAndPriceBetween(Long categoryId, String query, float minPrice, float maxPrice, Pageable pageable);
  
}
