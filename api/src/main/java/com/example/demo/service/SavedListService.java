package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.models.Product;
import com.example.demo.models.ProductListItem;
import com.example.demo.models.SavedList;
import com.example.demo.models.User;
import com.example.demo.repository.ProductListItemRepository;
import com.example.demo.repository.SavedListRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class SavedListService {

  @Autowired
  private SavedListRepository repository;

  @Autowired
  private ProductListItemRepository productListItemRepository;

  public SavedList save(SavedList list) {
    return repository.save(list);
  }

  public void delete(SavedList list) {
    repository.delete(list);
  }

  public Optional<ProductListItem> updateListItem(SavedList list, Product product, int amount) {
    Optional<ProductListItem> itemOptional = productListItemRepository.findByListIdAndProductId(list.getId(), product.getId());
    ProductListItem item = null;

    if (itemOptional.isPresent()) {
      if (amount > 0) {
        item = itemOptional.get();
        item.setAmount(amount);
      }
      else
        deleteListItem(itemOptional.get());

    }
    else if (amount > 0)
      item = new ProductListItem(amount, list.getProductList(), product);
    
    if (item != null) {
      productListItemRepository.save(item);
      return Optional.of(item);
    }
    
    return Optional.empty();
  }

  public void deleteListItem(ProductListItem item) {
    productListItemRepository.delete(item);
  }

  public Optional<SavedList> findById(long id) {
    return repository.findById(id);
  }

  public Page<SavedList> findAll(User user, Pageable page) {
    return repository.findByProductListUser(user, page);
  }

  public List<ProductListItem> getAllProducts(Long id) {
    return productListItemRepository.findByListId(id);
  }

}
