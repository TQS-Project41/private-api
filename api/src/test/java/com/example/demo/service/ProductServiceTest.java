package com.example.demo.service;

import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

  @Mock(lenient = true)
  private ProductRepository repository;

  @InjectMocks
  private ProductService service;

}
