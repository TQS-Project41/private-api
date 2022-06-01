package com.example.demo.Repository;

import com.example.demo.Models.UserAddress;
import com.example.demo.Models.UserAddressID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress, UserAddressID> {
  
}
