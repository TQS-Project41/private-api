package com.example.demo.repository;

import java.util.List;

import com.example.demo.models.User;
import com.example.demo.models.UserAddress;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {

  public List<UserAddress> findByUser(User user);
  
}
