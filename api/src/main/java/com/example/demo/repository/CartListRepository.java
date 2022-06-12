package com.example.demo.repository;

import com.example.demo.models.CartList;
import com.example.demo.models.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CartListRepository extends JpaRepository<CartList, Long> {

  public Optional<CartList> findFirstByProductListUserOrderByIdDesc(User user);
  
}
