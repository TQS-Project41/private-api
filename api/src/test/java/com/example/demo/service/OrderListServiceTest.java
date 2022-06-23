package com.example.demo.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.demo.models.CartList;
import com.example.demo.models.OrderList;
import com.example.demo.models.OrderProductItem;
import com.example.demo.models.Product;
import com.example.demo.models.ProductList;
import com.example.demo.models.ProductListItem;
import com.example.demo.models.Store;
import com.example.demo.models.User;
import com.example.demo.repository.CartListRepository;
import com.example.demo.repository.OrderListRepository;
import com.example.demo.repository.OrderProductItemRepository;
import com.example.demo.repository.ProductListItemRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

@ExtendWith(MockitoExtension.class)
public class OrderListServiceTest {

  @Mock(lenient = true)
  private OrderListRepository repository;

  @Mock(lenient = true)
  private OrderProductItemRepository orderProductItemRepository;

  @Mock(lenient = true)
  private CartListRepository cartListRepository;

  @Mock(lenient = true)
  private ProductListItemRepository productListItemRepository;

  @Mock(lenient = true)
  private CartListService cartListService;

  @InjectMocks
  private OrderListService service;

  @Test
  public void givenOrderExists_whenFindingById_thenReturnsPresentOptional() {

    OrderList list = new OrderList();

    Mockito.when(repository.findById(1L)).thenReturn(Optional.of(list));

    Optional<OrderList> searchList = repository.findById(1L);

    assertThat(searchList).isPresent().contains(list);

    Mockito.verify(repository, VerificationModeFactory.times(1)).findById(1L);

  }

  @Test
  public void whenFindingAllByUser_thenReturnsValidPage() {

    Pageable pageable = Pageable.unpaged();

    User user = new User();

    List<OrderList> orders = Arrays.asList(new OrderList(), new OrderList());

    Mockito.when(repository.findByProductListUser(user, pageable)).thenReturn(new PageImpl<>(orders));

    Page<OrderList> resultOrders = service.findAll(user, pageable);

    assertThat(resultOrders).containsExactlyElementsOf(orders);

    Mockito.verify(repository, VerificationModeFactory.times(1)).findByProductListUser(user, pageable);

  }

  @Test
  public void whenFindingAllByStore_thenReturnsValidPage() {

    Pageable pageable = Pageable.unpaged();

    Store store = new Store();

    List<OrderList> orders = Arrays.asList(new OrderList(), new OrderList());

    Mockito.when(repository.findByStoreId(store.getId(), pageable)).thenReturn(new PageImpl<>(orders));

    Page<OrderList> resultOrders = service.findAll(store, pageable);

    assertThat(resultOrders).containsExactlyElementsOf(orders);

    Mockito.verify(repository, VerificationModeFactory.times(1)).findByStoreId(0L, pageable);

  }

  @Test
  public void whenGettingAllOrderItems_thenReturnsList() {

    List<OrderProductItem> items = Arrays.asList(new OrderProductItem(), new OrderProductItem());

    Mockito.when(orderProductItemRepository.findByOrderListId(null)).thenReturn(items);

    List<OrderProductItem> foundItems = service.getAllOrderItems(null);

    assertThat(foundItems).containsExactlyElementsOf(items);

    Mockito.verify(orderProductItemRepository, VerificationModeFactory.times(1)).findByOrderListId(null);

  }

  @Test
  public void whenGettingAllItems_thenReturnsList() {

    List<ProductListItem> items = Arrays.asList(new ProductListItem(), new ProductListItem());

    Mockito.when(repository.findById(null)).thenReturn(Optional.of(new OrderList(new ProductList(), null, null, null)));
    Mockito.when(productListItemRepository.findByListId(0L)).thenReturn(items);

    List<ProductListItem> foundItems = service.getAllItems(null);

    assertThat(foundItems).containsExactlyElementsOf(items);

    Mockito.verify(repository, VerificationModeFactory.times(1)).findById(null);
    Mockito.verify(productListItemRepository, VerificationModeFactory.times(1)).findByListId(0L);

  }

  @Test
  public void whenCreatingFromCart_thenCartAndOrderHaveSameProductList() {

    User user = new User();
    ProductList list = new ProductList(user);
    CartList cart = new CartList(list);
    list.setId(1L);
    cart.setId(1L);

    List<ProductListItem> items = Arrays.asList(
      new ProductListItem(1, list, new Product()), 
      new ProductListItem(1, list, new Product()), 
      new ProductListItem(1, list, new Product())
    );

    Mockito.when(cartListService.getCurrentCart(user)).thenReturn(cart);
    Mockito.when(productListItemRepository.findByListId(null)).thenReturn(items);

    OrderList order = service.createFromCart(user, null, null, null, null);

    assertThat(order.getProductList()).isEqualTo(list);

    Mockito.verify(cartListService, VerificationModeFactory.times(1)).getCurrentCart(user);
    Mockito.verify(productListItemRepository, VerificationModeFactory.times(1)).findByListId(null);
    Mockito.verify(orderProductItemRepository, VerificationModeFactory.times(items.size())).save(Mockito.any(OrderProductItem.class));

  }

}
