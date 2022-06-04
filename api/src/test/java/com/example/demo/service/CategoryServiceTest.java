package com.example.demo.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.models.Category;
import com.example.demo.repository.CategoryRepository;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

  @Mock(lenient = true)
  private CategoryRepository repository;

  @InjectMocks
  private CategoryService service;

  private Category category1 = new Category("Fruit", true);
  private Category category2 = new Category("Cakes", true);
  private Category category3 = new Category("Vegetables", true);

  @BeforeEach
  public void setUp() {
    Category category4 = new Category("Fruit", true);
    category4.setId(1L);

    Mockito.when(repository.save(category1)).thenReturn(category4);
    Mockito.when(repository.findById(1L)).thenReturn(Optional.of(category1));
    Mockito.when(repository.findAll()).thenReturn(Arrays.asList(category1, category2, category3));
  }

  @Test
  public void whenGetAllCategories_thenReturnsCategoryList() {
    List<Category> categories = service.getAll();

    assertThat(categories).hasSize(3);

    assertThat(categories.get(1).getName()).isEqualTo("Cakes");
    assertThat(categories.get(1).getActive()).isTrue();

    Mockito.verify(repository, VerificationModeFactory.times(1)).findAll();
  }

  @Test
  public void whenSavingValidCategory_thenReturnsCategoryWithId() {    
    assertThat(category1.getId()).isZero();
    assertThat(repository.save(category1).getId()).isNotZero();

    Mockito.verify(repository, VerificationModeFactory.times(1)).save(category1);
  }

}
