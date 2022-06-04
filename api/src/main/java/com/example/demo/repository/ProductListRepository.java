package com.example.demo.repository;

import com.example.demo.models.ProductList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductListRepository extends JpaRepository<ProductList, Long> {
  
}
