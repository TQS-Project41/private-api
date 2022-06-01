package com.example.demo.Service;

import com.example.demo.Repository.CartListRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CartListService {
  
  @Autowired
  private CartListRepository repository;

}
