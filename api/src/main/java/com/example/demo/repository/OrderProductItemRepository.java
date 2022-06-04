package com.example.demo.repository;

import java.util.List;

import com.example.demo.models.OrderProductItem;
import com.example.demo.models.OrderProductItemId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderProductItemRepository extends JpaRepository<OrderProductItem, OrderProductItemId> {
  
  public List<OrderProductItem> findByOrderListId(Long orderListId);

}
