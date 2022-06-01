package com.example.demo.Service;

import java.util.Optional;

import com.example.demo.Models.User;
import com.example.demo.Repository.UserRepository;

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
  
}
