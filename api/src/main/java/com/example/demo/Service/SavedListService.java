package com.example.demo.Service;

import java.util.List;
import java.util.Optional;

import com.example.demo.Models.SavedList;
import com.example.demo.Repository.ProductListRepository;
import com.example.demo.Repository.SavedListRepository;

import org.springframework.beans.factory.annotation.Autowired;
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

  public SavedList findById(long id) {
    /* TODO this should filter by the user authenticated */
    Optional<SavedList> savedList = repository.findById(id);
    return savedList.isPresent() ? savedList.get() : null;
  }

  public List<SavedList> findAll() {
    /* TODO this list should be filtered by the user authenticated */
    return repository.findAll();
  }

}
