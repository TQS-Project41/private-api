package com.example.demo.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Optional;

import com.example.demo.models.Product;
import com.example.demo.repository.ProductRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

  @Mock(lenient = true)
  private ProductRepository repository;

  @InjectMocks
  private ProductService service;

  private Product product1 = new Product("Teste 1", 1, "Teste", true, null);
  private Product product2 = new Product("Teste 2", 2, "Teste", true, null);
  private Product product3 = new Product("Teste 3", 3, "Teste", true, null);

  private Pageable pageable = Pageable.unpaged();

  @BeforeEach
  public void setUp() {
    Product product4 = new Product("Teste 1", 1, "Teste", true, null);
    product4.setId(1L);

    Mockito.when(repository.save(product1)).thenReturn(product4);
    Mockito.when(repository.findById(1L)).thenReturn(Optional.of(product1));
    Mockito.when(repository.findById(10L)).thenReturn(Optional.empty());
    
    Mockito.when(repository.findByNameContainsAndPriceGreaterThan("", 0, pageable))
      .thenReturn(new PageImpl<>(Arrays.asList(product1, product2, product3), pageable, 3));
  }

  @Test
  public void whenSavingValidProduct_thenReturnsProductWithId() {
    assertThat(product1.getId()).isZero();
    assertThat(service.save(product1).getId()).isNotZero();

    Mockito.verify(repository, VerificationModeFactory.times(1)).save(product1);
  }

  @Test
  public void whenGettingProductById_thenReturnValidOptional() {
    assertThat(service.getById(1L)).isPresent();
    assertThat(service.getById(10L)).isNotPresent();

    Mockito.verify(repository, VerificationModeFactory.times(1)).findById(1L);
    Mockito.verify(repository, VerificationModeFactory.times(1)).findById(10L);
  }

  @Test
  public void whenGettingAllProductsWithNoFilters_thenReturnAllProductsPage() {
    assertThat(service.getAll(null, "", 0f, null, pageable).toList()).containsExactly(product1, product2, product3);

    Mockito.verify(repository, VerificationModeFactory.times(1)).findByNameContainsAndPriceGreaterThan("", 0, pageable);
  }

}
