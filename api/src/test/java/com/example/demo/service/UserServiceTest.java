package com.example.demo.service;

import com.example.demo.repository.UserRepository;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
  
  @Mock(lenient = true)
  private UserRepository repository;

  @InjectMocks
  private UserService service;

}