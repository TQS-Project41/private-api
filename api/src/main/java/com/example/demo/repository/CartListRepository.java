package com.example.demo.repository;

import com.example.demo.models.CartList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CartListRepository extends JpaRepository<CartList, Long> {
  
}
