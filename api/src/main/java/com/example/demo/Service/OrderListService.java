package com.example.demo.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.example.demo.Models.Address;
import com.example.demo.Models.OrderList;
import com.example.demo.Models.OrderProductItem;
import com.example.demo.Models.ProductList;
import com.example.demo.Models.ProductListItem;
import com.example.demo.Models.Store;
import com.example.demo.Repository.OrderListRepository;
import com.example.demo.Repository.OrderProductItemRepository;

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

  public OrderList createFromCart(Address address, Store store, Long deliveryId, Long deliveryTimestamp) {

    ProductList productList = cartListService.getCurrentCart().getProductList();

    Set<OrderProductItem> orderProductItem = new HashSet<>();

    OrderList orderList = repository.save(new OrderList(productList, orderProductItem, address, store, deliveryId, deliveryTimestamp));

    for (ProductListItem item : productList.getProductListItems()) {
      orderProductItem.add(orderProductItemRepository.save(new OrderProductItem(item.getProduct().getPrice(), orderList, item.getProduct())));
    }

    orderList.setOrderProductItem(orderProductItem);

    return repository.save(orderList);

  }

}
