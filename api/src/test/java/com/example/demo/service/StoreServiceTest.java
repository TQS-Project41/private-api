package com.example.demo.service;

import com.example.demo.Repository.StoreRepository;
import com.example.demo.Service.StoreService;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class StoreServiceTest {

  @Mock(lenient = true)
  private StoreRepository repository;

  @InjectMocks
  private StoreService service;

}
