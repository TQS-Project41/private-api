package com.example.demo.repository;

import javax.persistence.PersistenceException;

import com.example.demo.models.Address;
import com.example.demo.models.Category;
import com.example.demo.models.OrderList;
import com.example.demo.models.OrderProductItem;
import com.example.demo.models.OrderProductItemId;
import com.example.demo.models.Product;
import com.example.demo.models.ProductList;
import com.example.demo.models.Store;
import com.example.demo.models.User;
import com.example.demo.models.UserAddress;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OrderProductListRepositoryTest {
    @Container
    public static MySQLContainer<?> container = new MySQLContainer<>("mysql");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.username", container::getUsername);
    }

    @Autowired
    private OrderProductItemRepository rep;

    @Autowired
    private TestEntityManager entityManager;


@Test
    void testWhenCreateOrderProductItemAndFindById_thenReturnSameOrderProductItem() {
        Store store = new Store();
        store.setName("puma");
        User user = new User("alex200020011@gmail.com", "Serras", "aaaaa", LocalDate.of(2000, 5, 28), "911912912", false, true);
        entityManager.persistAndFlush(user);
        Address address= new Address("Portugal", "1903-221", "Aveiro", "Rua das Pombas");
        UserAddress userAddressTmp = new UserAddress(user, address);
        ProductList productListTmp = new ProductList();
        productListTmp.setUser(user);
        LocalDateTime deliveryTimestamp = LocalDateTime.of(2022, 10, 15, 19, 0);
        store.setAddress(address);
        Long deliveryId=1L;
        OrderList list = new OrderList(productListTmp, address, store, deliveryId, deliveryTimestamp);
        entityManager.persistAndFlush(user);
        entityManager.persistAndFlush(store);
        entityManager.persistAndFlush(address);
        entityManager.persistAndFlush(userAddressTmp);
        entityManager.persistAndFlush(productListTmp);
        entityManager.persistAndFlush(list);

        Category cat = new Category("Vegetais", false);
        
        Product product = new Product("Pilhas", 5.1f, "leve", true, cat);
        
        entityManager.persistAndFlush(cat);
        entityManager.persistAndFlush(product);

        OrderProductItem order = new OrderProductItem(7, list, product);
        entityManager.persistAndFlush(order);

       
        Optional<OrderProductItem> res = rep.findById(order.getId());
        assertThat(res).isPresent().contains(order);
    }

    @Test
    void testWhenFindByInvalidId_thenReturnNull() {
        Optional<OrderProductItem> res = rep.findById(new OrderProductItemId(-1L, -1L));
        assertThat(res).isNotPresent();
    }
    /* ------------------------------------------------- *
     * FIND TESTS                                  *
     * ------------------------------------------------- *
     */

    @Test
    void testGivenAddressAndFindByAll_thenReturnSameAddress() {
        Store store = new Store();
        store.setName("puma");
        User user = new User("alex200020011@gmail.com", "Serras", "aaaaa", LocalDate.of(2000, 5, 28), "911912912", false, true);
        entityManager.persistAndFlush(user);
        Address address= new Address("Portugal", "1903-221", "Aveiro", "Rua das Pombas");
        UserAddress userAddressTmp = new UserAddress(user, address);
        ProductList productListTmp = new ProductList();
        productListTmp.setUser(user);
        LocalDateTime deliveryTimestamp = LocalDateTime.of(2022, 10, 15, 19, 0);
        store.setAddress(address);
        Long deliveryId=1L;
        OrderList list = new OrderList(productListTmp, address, store, deliveryId, deliveryTimestamp);
        entityManager.persistAndFlush(user);
        entityManager.persistAndFlush(store);
        entityManager.persistAndFlush(address);
        entityManager.persistAndFlush(userAddressTmp);
        entityManager.persistAndFlush(productListTmp);
        entityManager.persistAndFlush(list);

        Category cat = new Category("Vegetais", false);

        Product product = new Product("Pilhas", 5.1f, "leve", true, cat);
        Product product2 = new Product("Pilhas reciclaveis", 12, "leve", true, cat);
        
        entityManager.persistAndFlush(cat);
        entityManager.persistAndFlush(product);
        entityManager.persistAndFlush(product2);


        OrderProductItem order = new OrderProductItem(7, list, product);
        OrderProductItem order2 = new OrderProductItem(7, list, product2);

        entityManager.persistAndFlush(order);
        entityManager.persistAndFlush(order2);



        List<OrderProductItem> all = rep.findAll();

        assertThat(all).isNotNull();
        assertThat(all)
                .hasSize(2)
                .extracting(OrderProductItem::getProduct)
                .contains(order.getProduct(), order2.getProduct());
       
    }

    @Test
    void testGivenNoCategory_whenFindAll_thenReturnEmpty() {
        List<OrderProductItem> all = rep.findAll();
        assertThat(all).isNotNull().isEmpty();
    }

    @Test
    void testWhenCreateInvalidCategory_thenReturnException() {
        OrderProductItem  x = new OrderProductItem();
        assertThrows(PersistenceException.class, () -> {
            entityManager.persistAndFlush(x);
        });
    }

    @Test
    void givenMultipleOrders_whenFindingItemsByOrderId_thenReturnOnlyItsItems() {
        float productPrice = 14.78f;
        Category category = new Category("Vegetais", true);
        Product product = new Product("Couve-Flor", productPrice, "Saboroso", true, category);

        User user = new User("pedro.dld@ua.pt", "Pedro Duarte", "password", LocalDate.of(2001, 11, 5), "249 311 804", true, true);
        Address address = new Address("country", "zipcode", "city", "address");
        Store store = new Store("Store", address);

        ProductList productList1 = new ProductList(user);
        OrderList orderList1 = new OrderList(productList1, address, store, 1L);

        ProductList productList2 = new ProductList(user);
        OrderList orderList2 = new OrderList(productList2, address, store, 2L);

        OrderProductItem item = new OrderProductItem(productPrice, orderList1, product);

        entityManager.persistAndFlush(category);
        entityManager.persistAndFlush(product);
        entityManager.persistAndFlush(user);
        entityManager.persistAndFlush(address);
        entityManager.persistAndFlush(store);
        entityManager.persistAndFlush(productList1);
        entityManager.persistAndFlush(orderList1);
        entityManager.persistAndFlush(productList2);
        entityManager.persistAndFlush(orderList2);
        entityManager.persistAndFlush(item);

        assertEquals(1, rep.findByOrderListId(orderList1.getId()).size());
        assertEquals(0, rep.findByOrderListId(orderList2.getId()).size());
    }

}
