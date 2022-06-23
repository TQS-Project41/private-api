package com.example.demo.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import com.example.demo.models.Product;
import com.example.demo.models.ProductList;
import com.example.demo.models.ProductListItem;
import com.example.demo.models.SavedList;
import com.example.demo.models.User;
import com.example.demo.repository.ProductListItemRepository;
import com.example.demo.repository.SavedListRepository;

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
public class SavedListServiceTest {

  @Mock(lenient = true)
  private SavedListRepository repository;

  @Mock(lenient = true)
  private ProductListItemRepository productListItemRepository;

  @InjectMocks
  private SavedListService service;

  @Test
  public void whenSavingValidSavedList_thenReturnsListWithId() {
    SavedList list = new SavedList(new ProductList(null), "Cenas");
    SavedList listSaved = new SavedList(new ProductList(null), "Cenas");

    listSaved.setId(1L);

    Mockito.when(repository.save(list)).thenReturn(listSaved);

    assertThat(list.getId()).isNull();
    assertThat(service.save(list).getId()).isEqualTo(1L);

    Mockito.verify(repository, VerificationModeFactory.times(1)).save(list);
  }

  @Test
  public void testDelete() {
    SavedList list = new SavedList(new ProductList(null), "Cenas");
    service.delete(list);

    Mockito.verify(repository, VerificationModeFactory.times(1)).delete(list);
  }

  @Test
  public void whenUpdatingListItemWithPositiveAmount_returnsUpdatedItem() {
    Product product = new Product();
    product.setId(1L);

    ProductList productList = new ProductList();
    productList.setId(1L);

    ProductListItem item = new ProductListItem(15, productList, product);

    SavedList list = new SavedList(productList, "Cenas");
    list.setId(1L);

    Mockito.when(productListItemRepository.findByListIdAndProductId(1L, 1L)).thenReturn(Optional.of(item));

    Optional<ProductListItem> result = service.updateListItem(list, product, 10);

    assertThat(result).isPresent();
    assertThat(result.get().getList()).isEqualTo(productList);
    assertThat(result.get().getProduct()).isEqualTo(product);
    assertThat(result.get().getAmount()).isEqualTo(10);

    Mockito.verify(productListItemRepository, VerificationModeFactory.times(1)).findByListIdAndProductId(1L, 1L);
    Mockito.verify(productListItemRepository, VerificationModeFactory.times(1)).save(item);
  }

  @Test
  public void whenUpdatingListItemWithNonPositiveAmount_returnsEmpty() {
    Product product = new Product();
    product.setId(1L);

    ProductList productList = new ProductList();
    productList.setId(1L);

    ProductListItem item = new ProductListItem(15, productList, product);

    SavedList list = new SavedList(productList, "Cenas");
    list.setId(1L);

    Mockito.when(productListItemRepository.findByListIdAndProductId(1L, 1L)).thenReturn(Optional.of(item));

    Optional<ProductListItem> result = service.updateListItem(list, product, 0);

    assertThat(result).isNotPresent();

    Mockito.verify(productListItemRepository, VerificationModeFactory.times(1)).findByListIdAndProductId(1L, 1L);
    Mockito.verify(productListItemRepository, VerificationModeFactory.times(1)).delete(item);
    Mockito.verify(productListItemRepository, VerificationModeFactory.times(0)).save(item);
  }

  @Test
  public void testDeleteListItem() {
    ProductListItem item = new ProductListItem(15, new ProductList(), new Product());

    service.deleteListItem(item);

    Mockito.verify(productListItemRepository, VerificationModeFactory.times(1)).delete(item);
  }

  @Test
  public void givenCreatedSavedList_whenFindingById_thenReturnIt() {
    SavedList list = new SavedList(null, "Cenas");

    Mockito.when(repository.findById(1L)).thenReturn(Optional.of(list));

    Optional<SavedList> result = service.findById(1L);

    assertThat(result).isPresent();
    assertThat(result.get().getName()).isEqualTo("Cenas");

    Mockito.verify(repository, VerificationModeFactory.times(1)).findById(1L);
  }

  @Test
  public void givenMultipleCreatedSavedLists_whenFindingAll_thenReturnAll() {
    User user = new User("pedro.dld@ua.pt", "Pedro Duarte", "password", LocalDate.of(2001, 11, 5), "249 311 804", true, true);

    SavedList list1 = new SavedList(null, "Cenas");
    SavedList list2 = new SavedList(null, "Escritorio");
    SavedList list3 = new SavedList(null, "Casa");

    Pageable pageable = Pageable.unpaged();

    Mockito.when(repository.findByProductListUser(user, pageable)).thenReturn(new PageImpl<>(Arrays.asList(list1, list2, list3)));

    assertThat(service.findAll(user, Pageable.unpaged())).containsExactly(list1, list2, list3);

    Mockito.verify(repository, VerificationModeFactory.times(1)).findByProductListUser(user, pageable);
  }

}
