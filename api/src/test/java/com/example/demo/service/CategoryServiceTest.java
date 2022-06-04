package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.demo.models.Category;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.service.CategoryService;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

  @Mock(lenient = true)
  private CategoryRepository repository;

  @InjectMocks
  private CategoryService service;

  @BeforeEach
  public void setUp() {
    Category category1 = new Category("Fruit", true);
    Category category2 = new Category("Cakes", true);
    Category category3 = new Category("Vegetables", true);

    Mockito.when(repository.findById(1L)).thenReturn(Optional.of(category1));
    Mockito.when(repository.findAll()).thenReturn(Arrays.asList(category1, category2, category3));
  }

  @Test
  public void whenGetAllCategories_thenReturnsCategoryList() {
    List<Category> categories = service.getAll();

    assertEquals(3, categories.size());

    assertEquals("Cakes", categories.get(1).getName());
    assertEquals(true, categories.get(1).getActive());

    Mockito.verify(repository, VerificationModeFactory.times(1)).findAll();
  }

}
