package com.example.demo.Repository;

import com.example.demo.Models.ProductList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductListRepository extends JpaRepository<ProductList, Long> {
  
}
