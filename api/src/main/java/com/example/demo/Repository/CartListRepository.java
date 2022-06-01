package com.example.demo.Repository;

import com.example.demo.Models.CartList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CartListRepository extends JpaRepository<CartList, Long> {
  
}
