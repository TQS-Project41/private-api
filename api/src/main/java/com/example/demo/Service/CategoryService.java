package com.example.demo.Service;

import java.util.List;

import com.example.demo.Models.Category;
import com.example.demo.Repository.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CategoryService {

  @Autowired
  private CategoryRepository repository;

  public Category save(Category category) {
    return repository.save(category);
  }

  public List<Category> getAll() {
    return repository.findAll();
  }

}
