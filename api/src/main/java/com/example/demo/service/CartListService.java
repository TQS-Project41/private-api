package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.models.CartList;
import com.example.demo.models.OrderList;
import com.example.demo.models.ProductList;
import com.example.demo.repository.CartListRepository;
import com.example.demo.repository.OrderListRepository;
import com.example.demo.repository.ProductListRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CartListService {
  
  @Autowired
  private CartListRepository repository;

  @Autowired
  private ProductListRepository productListRepository;

  @Autowired
  private OrderListRepository orderListRepository;

  /* TODO Code created only for demonstration, mostly useless */
  public CartList getCurrentCart() {
    CartList currentCart = null;

    List<CartList> cartLists = repository.findAll();

    if (!cartLists.isEmpty()) {
      CartList cartList = cartLists.get(cartLists.size()-1);
      Optional<OrderList> orderList =  orderListRepository.findById(cartList.getProductList().getId());
      if (orderList.isEmpty()) currentCart = cartList;
    }

    if (currentCart == null) {
      currentCart = repository.save(new CartList(productListRepository.save(new ProductList())));
    }

    return currentCart;
  }

}
