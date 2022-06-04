package com.example.demo.service;

import java.util.Optional;

import com.example.demo.models.User;
import com.example.demo.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

  @Autowired
  private UserRepository repository;

  public User save(User user) {
    return repository.save(user);
  }

  public User getById(long id) {
    Optional<User> user = repository.findById(id);
    return user.isPresent() ? user.get() : null;
  }

  public User getByEmailAndPassword(String email, String password) {
    return repository.findByEmailAndPassword(email, password);
  }
  
}
