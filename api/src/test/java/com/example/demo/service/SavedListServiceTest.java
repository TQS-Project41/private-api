package com.example.demo.service;

import com.example.demo.repository.SavedListRepository;
import com.example.demo.service.SavedListService;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SavedListServiceTest {

  @Mock(lenient = true)
  private SavedListRepository repository;

  @InjectMocks
  private SavedListService service;

}
