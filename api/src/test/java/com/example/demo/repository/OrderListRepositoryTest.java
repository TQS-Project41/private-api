package com.example.demo.repository;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;

import com.example.demo.Models.Address;
import com.example.demo.Models.CartList;
import com.example.demo.Models.Category;
import com.example.demo.Models.OrderList;
import com.example.demo.Models.OrderProductItem;
import com.example.demo.Models.Product;
import com.example.demo.Models.ProductList;
import com.example.demo.Models.Store;
import com.example.demo.Models.User;
import com.example.demo.Models.UserAddress;
import com.example.demo.Repository.AddressRepository;
import com.example.demo.Repository.CartListRepository;
import com.example.demo.Repository.CategoryRepository;
import com.example.demo.Repository.OrderListRepository;
import com.example.demo.Repository.ProductListRepository;
import com.example.demo.Repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

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
        Set<ProductList> productList = new HashSet<>();
        Set<UserAddress> userAddress=new HashSet<>();
        User user = new User("alex200020011@gmail.com", "Serras", "aaaaa", new Date(2000, 5, 28), "911912912", false, true, productList, userAddress);
        entityManager.persistAndFlush(user);
        Address address= new Address("Portugal", "1903-221", "Aveiro", "Rua das Pombas", store);
        UserAddress userAddressTmp = new UserAddress(user, address);
        Set<OrderProductItem> orderProductItem=new HashSet<>();
        ProductList productListTmp = new ProductList();
        productListTmp.setUser(user);
        Set<OrderProductItem> productListItems=new HashSet<>();
        Long deliveryTimestamp=111111111111L;
        store.setAddress(address);
        Long deliveryId=1L;
        OrderList list = new OrderList(productListTmp, orderProductItem, address, store, deliveryId, deliveryTimestamp);
        list.setProductListItems(productListItems);
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
        Store store = new Store();
        store.setName("puma");
        Set<ProductList> productList = new HashSet<>();
        Set<UserAddress> userAddress=new HashSet<>();
        User user = new User("alex200020011@gmail.com", "Serras", "aaaaa", new Date(2000, 5, 28), "911912912", false, true, productList, userAddress);
        Address address= new Address("Portugal", "1903-221", "Aveiro", "Rua das Pombas", store);
        UserAddress userAddressTmp = new UserAddress(user, address);
        Set<OrderProductItem> orderProductItem=new HashSet<>();
        ProductList productListTmp = new ProductList();
        productListTmp.setUser(user);
        Set<OrderProductItem> productListItems=new HashSet<>();
        Long deliveryTimestamp=111111111111L;
        store.setAddress(address);
        Long deliveryId=1L;
        OrderList list = new OrderList(productListTmp, orderProductItem, address, store, deliveryId, deliveryTimestamp);
        list.setProductListItems(productListItems);
        entityManager.persistAndFlush(user);
        entityManager.persistAndFlush(store);
        entityManager.persistAndFlush(address);
        entityManager.persistAndFlush(userAddressTmp);
        entityManager.persistAndFlush(productListTmp);
        entityManager.persistAndFlush(list);


        Store store1 = new Store();
        store1.setName("joma");
        Set<ProductList> productList1 = new HashSet<>();
        Set<UserAddress> userAddress1=new HashSet<>();
        User user1 = new User("alex200020011@gmail.com", "Serras", "aaaaa", new Date(2000, 5, 28), "911912912", false, true, productList1, userAddress1);
        entityManager.persistAndFlush(user);
        Address address1= new Address("Portugal", "2222-221", "Santarem", "Rua das ss", store1);
        UserAddress userAddressTmp1 = new UserAddress(user1, address1);
        Set<OrderProductItem> orderProductItem1=new HashSet<>();
        ProductList productListTmp1 = new ProductList();
        productListTmp1.setUser(user1);
        Set<OrderProductItem> productListItems1=new HashSet<>();
        Long deliveryTimestamp1=111111111111L;
        store1.setAddress(address1);
        Long deliveryId1=11L;
        OrderList list1 = new OrderList(productListTmp1, orderProductItem1, address1, store1, deliveryId1, deliveryTimestamp1);
        list1.setProductListItems(productListItems1);

        entityManager.persistAndFlush(store1);
        entityManager.persistAndFlush(address1);
        entityManager.persistAndFlush(userAddressTmp1);
        entityManager.persistAndFlush(productListTmp1);
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
