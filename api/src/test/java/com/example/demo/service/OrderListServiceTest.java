package com.example.demo.service;

import com.example.demo.repository.OrderListRepository;
import com.example.demo.service.OrderListService;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OrderListServiceTest {

  @Mock(lenient = true)
  private OrderListRepository repository;

  @InjectMocks
  private OrderListService service;

}
