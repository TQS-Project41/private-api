package com.example.demo.repository;

import javax.persistence.PersistenceException;

import com.example.demo.models.Address;
import com.example.demo.models.OrderList;
import com.example.demo.models.ProductList;
import com.example.demo.models.Store;
import com.example.demo.models.User;
import com.example.demo.models.UserAddress;
import com.example.demo.repository.OrderListRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

import java.util.Date;
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
public class OrderListRepositoryTest {
    @Container
    public static MySQLContainer container = new MySQLContainer()
        .withUsername("user")
        .withPassword("user")
        .withDatabaseName("tqs_final_41");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.username", container::getUsername);
    }

    @Autowired
    private OrderListRepository rep;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void testWhenCreateOrderListAndFindById_thenReturnSameOrderList() {
        Store store = new Store();
        store.setName("puma");
        User user = new User("alex200020011@gmail.com", "Serras", "aaaaa", new Date(2000, 5, 28), "911912912", false, true);
        entityManager.persistAndFlush(user);
        Address address= new Address("Portugal", "1903-221", "Aveiro", "Rua das Pombas");
        UserAddress userAddressTmp = new UserAddress(user, address);
        ProductList productListTmp = new ProductList();
        productListTmp.setUser(user);
        Long deliveryTimestamp=111111111111L;
        store.setAddress(address);
        Long deliveryId=1L;
        OrderList list = new OrderList(productListTmp, address, store, deliveryId, deliveryTimestamp);
        entityManager.persistAndFlush(user);
        entityManager.persistAndFlush(store);
        entityManager.persistAndFlush(address);
        entityManager.persistAndFlush(userAddressTmp);
        entityManager.persistAndFlush(productListTmp);
        entityManager.persistAndFlush(list);


        
        
        Optional<OrderList> res = rep.findById(list.getId());
        assertThat(res).isPresent().contains(list);
    }

    @Test
    void testWhenFindByInvalidId_thenReturnNull() {
        Optional<OrderList> res = rep.findById(-1L);
        assertThat(res).isNotPresent();
    }
    /* ------------------------------------------------- *
     * FIND TESTS                                  *
     * ------------------------------------------------- *
     */
    @Test
    void testGivenOrderListAndFindByAll_thenReturnSameOrderList() {
        Address address = new Address("Portugal", "1903-221", "Aveiro", "Rua das Pombas");
        Store store = new Store("puma", address);        
        User user = new User("alex200020011@gmail.com", "Serras", "aaaaa", new Date(2000, 5, 28), "911912912", false, true);
        ProductList productList = new ProductList(user);
        Long deliveryTimestamp=111111111111L;
        Long deliveryId=1L;
        OrderList list = new OrderList(productList, address, store, deliveryId, deliveryTimestamp);

        entityManager.persistAndFlush(user);
        entityManager.persistAndFlush(store);
        entityManager.persistAndFlush(address);
        entityManager.persistAndFlush(productList);
        entityManager.persistAndFlush(list);

        Address address1 = new Address("Portugal", "2222-221", "Santarem", "Rua das ss");
        Store store1 = new Store("joma", address);
        User user1 = new User("alex200020011@gmail.com", "Serras", "aaaaa", new Date(2000, 5, 28), "911912912", false, true);
        ProductList productList1 = new ProductList(user1);
        Long deliveryTimestamp1=111111111111L;
        Long deliveryId1=11L;
        OrderList list1 = new OrderList(productList1, address1, store1, deliveryId1, deliveryTimestamp1);

        entityManager.persistAndFlush(user1);
        entityManager.persistAndFlush(store1);
        entityManager.persistAndFlush(address1);
        entityManager.persistAndFlush(productList1);
        entityManager.persistAndFlush(list1);

        List<OrderList> all = rep.findAll();

        assertThat(all).isNotNull();
        assertThat(all)
                .hasSize(2)
                .extracting(OrderList::getAddress)
                .contains(list.getAddress(), list1.getAddress());
       
    }

    @Test
    void testGivenNoCartList_whenFindAll_thenReturnEmpty() {
        List<OrderList> all = rep.findAll();
        assertThat(all).isNotNull().isEmpty();
    }

    @Test
    void testWhenCreateInvalidCartListAndFindById_thenReturnException() {
        OrderList  x = new OrderList();
        assertThrows(PersistenceException.class, () -> {
            entityManager.persistAndFlush(x);
        });
    }
}
