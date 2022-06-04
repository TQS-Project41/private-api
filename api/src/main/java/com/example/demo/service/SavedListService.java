package com.example.demo.service;

import java.util.Optional;

import com.example.demo.models.Product;
import com.example.demo.models.SavedList;
import com.example.demo.models.User;
import com.example.demo.repository.ProductListRepository;
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
  private ProductListRepository productListRepository;

  public SavedList save(SavedList list) {
    list.setProductList(productListRepository.save(list.getProductList()));
    return repository.save(list);
  }

  public void delete(SavedList list) {
    repository.delete(list);
  }

  public boolean updateListItem(long id, Product product, int amount) {
    return false;
  }

  public void deleteListItem(long id) {

  }

  public SavedList createFromList(long id, String name) {

    return null;

  }

  public SavedList findById(long id) {
    Optional<SavedList> savedList = repository.findById(id);
    return savedList.isPresent() ? savedList.get() : null;
  }

  public Page<SavedList> findAll(User user, Pageable page) {
    return null;
  }

}
