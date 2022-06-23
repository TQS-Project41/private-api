package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.models.CartList;
import com.example.demo.models.Product;
import com.example.demo.models.ProductList;
import com.example.demo.models.ProductListItem;
import com.example.demo.models.SavedList;
import com.example.demo.models.User;
import com.example.demo.repository.CartListRepository;
import com.example.demo.repository.OrderListRepository;
import com.example.demo.repository.ProductListItemRepository;
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
  private ProductListItemRepository productListItemRepository;

  @Autowired
  private OrderListRepository orderListRepository;

  public CartList getCurrentCart(User user) {
    Optional<CartList> cartOptional = repository.findFirstByProductListUserOrderByIdDesc(user);
    CartList cart = null;

    if (cartOptional.isPresent()) {
      cart = cartOptional.get();

      // Ignore last cart if it is already an order
      if (orderListRepository.findById(cart.getProductList().getId()).isPresent())
        cart = null;
    }

    if (cart == null) {
      ProductList list = new ProductList(user);
      cart = new CartList(list);

      productListRepository.save(list);
      repository.save(cart);
    }

    return cart;
  }

  public Optional<ProductListItem> updateCartItem(User user, Product product, int amount) {    
    CartList cart = getCurrentCart(user);

    Optional<ProductListItem> itemOptional = productListItemRepository.findByListIdAndProductId(cart.getId(), product.getId());
    ProductListItem item = null;

    if (itemOptional.isPresent()) {
      if (amount > 0) {
        item = itemOptional.get();
        item.setAmount(amount);
      } else
        deleteListItem(itemOptional.get());

    } else if (amount > 0)
      item = new ProductListItem(amount, cart.getProductList(), product);

    if (item != null) {
      productListItemRepository.save(item);
      return Optional.of(item);
    }

    return Optional.empty();
  }

  public void deleteListItem(ProductListItem item) {
    productListItemRepository.delete(item);
  }

  public List<ProductListItem> getCurrentCartItems(User user) {

    CartList cart = getCurrentCart(user);

    return productListItemRepository.findByListId(cart.getId());

  }

  public List<ProductListItem> addItemsFromSavedList(User user, SavedList list) {

    List<ProductListItem> items = productListItemRepository.findByListId(list.getId());

    for (ProductListItem item : items) {
      updateCartItem(user, item.getProduct(), item.getAmount());
    }

    return getCurrentCartItems(user);

  }

  public void cleanCart(User user) {

    productListItemRepository.deleteAll(getCurrentCartItems(user));

  }

}
