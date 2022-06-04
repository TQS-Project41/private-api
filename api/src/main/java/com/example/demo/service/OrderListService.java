package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.example.demo.models.Address;
import com.example.demo.models.OrderList;
import com.example.demo.models.Store;
import com.example.demo.repository.OrderListRepository;
import com.example.demo.repository.OrderProductItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OrderListService {

  @Autowired
  private OrderListRepository repository;

  @Autowired
  private OrderProductItemRepository orderProductItemRepository;

  @Autowired
  private CartListService cartListService;

  public OrderList findById(Long id) {
    /* TODO this should be filtered by the user authenticated */
    Optional<OrderList> orderList = repository.findById(id);
    return orderList.isPresent() ? orderList.get() : null;
  }

  public List<OrderList> findAll() {
    /* TODO this list should be filtered by the user authenticated */
    return repository.findAll();
  }

  public OrderList createFromCart(Address address, Store store, Long deliveryId, LocalDateTime deliveryTimestamp) {

    return null;

  }

}
