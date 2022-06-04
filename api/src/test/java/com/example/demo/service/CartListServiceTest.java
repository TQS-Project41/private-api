package com.example.demo.service;

import com.example.demo.repository.CartListRepository;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CartListServiceTest {

  @Mock(lenient = true)
  private CartListRepository repository;

  @InjectMocks
  private CartListService service;

}