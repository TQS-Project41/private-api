package com.example.demo.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.demo.models.CartList;
import com.example.demo.models.Product;
import com.example.demo.models.ProductList;
import com.example.demo.models.ProductListItem;
import com.example.demo.models.SavedList;
import com.example.demo.models.User;
import com.example.demo.repository.CartListRepository;
import com.example.demo.repository.OrderListRepository;
import com.example.demo.repository.ProductListItemRepository;
import com.example.demo.repository.ProductListRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CartListServiceTest {

  @Mock(lenient = true)
  private CartListRepository repository;

  @Mock(lenient = true)
  private ProductListRepository productListRepository;

  @Mock(lenient = true)
  private ProductListItemRepository productListItemRepository;

  @Mock(lenient = true)
  private OrderListRepository orderListRepository;

  @InjectMocks
  private CartListService service;

  @Test
  public void whenGettingCurrentCartForTheFirstTime_thenCreateNewCart() {
    Mockito.when(repository.findFirstByProductListUserOrderByIdDesc(null)).thenReturn(Optional.empty());

    CartList cart = service.getCurrentCart(null);

    assertThat(cart).isNotNull();

    Mockito.verify(repository, VerificationModeFactory.times(1)).findFirstByProductListUserOrderByIdDesc(null);
    Mockito.verify(productListRepository, VerificationModeFactory.times(1)).save(Mockito.any(ProductList.class));
    Mockito.verify(repository, VerificationModeFactory.times(1)).save(Mockito.any(CartList.class));
  }

  @Test
  public void givenCartExists_whenGettingCurrentCart_thenReturnCurrentUserCart() {
    User user = new User();
    ProductList productList = new ProductList(user);
    CartList cart = new CartList(productList);
    
    Mockito.when(repository.findFirstByProductListUserOrderByIdDesc(user)).thenReturn(Optional.of(cart));

    CartList resultCart = service.getCurrentCart(user);

    assertThat(resultCart).isNotNull();
    assertThat(resultCart.getProductList()).isEqualTo(productList);
    assertThat(resultCart.getProductList().getUser()).isEqualTo(user);

    Mockito.verify(repository, VerificationModeFactory.times(1)).findFirstByProductListUserOrderByIdDesc(user);
    Mockito.verify(orderListRepository, VerificationModeFactory.times(1)).findById(0L);
  }

  @Test
  public void whenUpdatingListItemWithPositiveAmount_returnsUpdatedItem() {
    Product product = new Product();
    product.setId(1L);

    ProductList productList = new ProductList();
    productList.setId(1L);

    ProductListItem item = new ProductListItem(15, productList, product);

    CartList cart = new CartList(productList);
    cart.setId(1L);

    Mockito.when(repository.findFirstByProductListUserOrderByIdDesc(null)).thenReturn(Optional.of(cart));
    Mockito.when(productListItemRepository.findByListIdAndProductId(1L, 1L)).thenReturn(Optional.of(item));

    Optional<ProductListItem> result = service.updateCartItem(null, product, 10);

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

    CartList list = new CartList(productList);
    list.setId(1L);

    Mockito.when(repository.findFirstByProductListUserOrderByIdDesc(null)).thenReturn(Optional.of(list));
    Mockito.when(productListItemRepository.findByListIdAndProductId(1L, 1L)).thenReturn(Optional.of(item));

    Optional<ProductListItem> result = service.updateCartItem(null, product, 0);

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
  public void whenGettingCartItemsByUser_thenReturnsOnlyHisItems() {
    User user = new User("pedro.dld@ua.pt", "Pedro Duarte", "password", LocalDate.of(2001, 11, 5), "249 311 804", true,
        true);

    ProductList productList = new ProductList(user);
    ProductListItem item = new ProductListItem();

    CartList cart = new CartList(productList);

    Mockito.when(repository.findFirstByProductListUserOrderByIdDesc(user)).thenReturn(Optional.of(cart));
    Mockito.when(orderListRepository.findById(cart.getId())).thenReturn(Optional.empty());
    Mockito.when(productListItemRepository.findByListId(cart.getId())).thenReturn(Arrays.asList(item));

    List<ProductListItem> items = service.getCurrentCartItems(user);

    assertThat(items).containsExactly(item);

    Mockito.verify(repository, VerificationModeFactory.times(1)).findFirstByProductListUserOrderByIdDesc(user);
    Mockito.verify(orderListRepository, VerificationModeFactory.times(1)).findById(0L);
    Mockito.verify(productListItemRepository, VerificationModeFactory.times(1)).findByListId(null);
  }

  @Test
  public void whenAddingSavedListToCart_thenCopyAllItems() {

    User user = new User();

    ProductList productList = new ProductList(user);
    SavedList list = new SavedList(productList, "Cenas");
    productList.setId(1L);
    list.setId(1L);

    Product product = new Product();
    ProductListItem item = new ProductListItem(10, productList, product);

    ProductList cartList = new ProductList(user);
    CartList cart = new CartList(cartList);
    cartList.setId(2L);
    cart.setId(2L);

    Mockito.when(repository.findFirstByProductListUserOrderByIdDesc(user)).thenReturn(Optional.of(cart));
    Mockito.when(orderListRepository.findById(cart.getId())).thenReturn(Optional.empty());
    Mockito.when(productListItemRepository.findByListId(list.getId())).thenReturn(Arrays.asList(item));

    service.addItemsFromSavedList(user, list);

    Mockito.verify(repository, VerificationModeFactory.atLeast(1)).findFirstByProductListUserOrderByIdDesc(user);
    Mockito.verify(orderListRepository, VerificationModeFactory.atLeast(1)).findById(2L);
    Mockito.verify(productListItemRepository, VerificationModeFactory.times(1)).findByListId(1L);
    Mockito.verify(productListItemRepository, VerificationModeFactory.times(1))
        .save(Mockito.any(ProductListItem.class));

  }

  @Test
  public void whenCleaningCart_thenDeleteAllItems() {

    ProductList cartList = new ProductList();
    CartList cart = new CartList(cartList);
    
    List<ProductListItem> items = Arrays.asList(new ProductListItem(), new ProductListItem(), new ProductListItem());

    Mockito.when(repository.findFirstByProductListUserOrderByIdDesc(null)).thenReturn(Optional.of(cart));
    Mockito.when(orderListRepository.findById(cart.getId())).thenReturn(Optional.empty());
    Mockito.when(productListItemRepository.findByListId(cart.getId())).thenReturn(items);

    service.cleanCart(null);

    Mockito.verify(repository, VerificationModeFactory.times(1)).findFirstByProductListUserOrderByIdDesc(null);
    Mockito.verify(orderListRepository, VerificationModeFactory.times(1)).findById(0L);
    Mockito.verify(productListItemRepository, VerificationModeFactory.times(1)).deleteAll(items);

  }

}
