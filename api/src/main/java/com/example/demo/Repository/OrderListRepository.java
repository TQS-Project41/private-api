package com.example.demo.Repository;

import com.example.demo.Models.OrderList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderListRepository extends JpaRepository<OrderList, Long> {
  
}
