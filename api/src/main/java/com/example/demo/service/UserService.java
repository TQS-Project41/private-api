package com.example.demo.service;

import java.util.Optional;

import com.example.demo.models.User;
import com.example.demo.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
public class UserService {

  @Autowired
  private UserRepository repository;

  public User save(User user) {
    return repository.save(user);
  }

  public Optional<User> getById(long id) {
    return repository.findById(id);
  }

  public Optional<User> getByEmailAndPassword(String email, String password) {
    return repository.findByEmailAndPassword(email, password);
  }

  public Optional<User> getAuthenticatedUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    
    if (authentication == null) return Optional.empty();

    Object principal = authentication.getPrincipal();

    if (principal == null) return Optional.empty();

    return Optional.of((User) principal);
  }
  
}
