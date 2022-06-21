package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.example.demo.models.Address;
import com.example.demo.models.CartList;
import com.example.demo.models.OrderList;
import com.example.demo.models.OrderProductItem;
import com.example.demo.models.ProductListItem;
import com.example.demo.models.Store;
import com.example.demo.models.User;
import com.example.demo.repository.OrderListRepository;
import com.example.demo.repository.OrderProductItemRepository;
import com.example.demo.repository.ProductListItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class OrderListService {

  @Autowired
  private OrderListRepository repository;

  @Autowired
  private OrderProductItemRepository orderProductItemRepository;

  @Autowired
  private ProductListItemRepository productListItemRepository;

  @Autowired
  private CartListService cartListService;

  public Optional<OrderList> findById(Long id) {
    return repository.findById(id);
  }

  public Page<OrderList> findAll(User user, Pageable page) {
    return repository.findByProductListUser(user, page);
  }

  public Page<OrderList> findAll(Store store, Pageable page) {
    return repository.findByStoreId(store.getId(), page);
  }

  public List<OrderProductItem> getAllOrderItems(Long orderId) {
    return orderProductItemRepository.findByOrderListId(orderId);
  }

  public OrderList createFromCart(User user, Address address, Store store, Long deliveryId, LocalDateTime deliveryTimestamp) {
    CartList cart = cartListService.getCurrentCart(user);

    OrderList order = new OrderList(cart.getProductList(), address, store, deliveryId, deliveryTimestamp);
    repository.save(order);

    List<ProductListItem> items = productListItemRepository.findByListId(order.getId());

    for (ProductListItem item : items) {
      orderProductItemRepository.save(new OrderProductItem(item.getProduct().getPrice(), order, item.getProduct()));
    }

    return order;
  }

  public List<ProductListItem> getAllProducts(Long id) {
    return productListItemRepository.findByListId(id);
  }

}
