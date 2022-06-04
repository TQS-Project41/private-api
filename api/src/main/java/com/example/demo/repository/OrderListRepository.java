package com.example.demo.repository;

import com.example.demo.models.OrderList;
import com.example.demo.models.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderListRepository extends JpaRepository<OrderList, Long> {

  public Page<OrderList> findByProductListUser(User user, Pageable page);

  public Page<OrderList> findByStoreId(Long storeId, Pageable page);
  
}
