package com.example.demo.Repository;

import com.example.demo.Models.OrderProductItem;
import com.example.demo.Models.OrderProductItemId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderProductItemRepository extends JpaRepository<OrderProductItem, OrderProductItemId> {
  
}
