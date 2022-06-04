package com.example.demo.repository;

import java.util.List;

import com.example.demo.models.ListItemId;
import com.example.demo.models.OrderProductItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderProductItemRepository extends JpaRepository<OrderProductItem, ListItemId> {
  
  public List<OrderProductItem> findByOrderListId(Long orderListId);

}
