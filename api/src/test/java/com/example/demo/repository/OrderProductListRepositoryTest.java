package com.example.demo.repository;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;

import com.example.demo.Models.Address;
import com.example.demo.Models.CartList;
import com.example.demo.Models.Category;
import com.example.demo.Models.OrderList;
import com.example.demo.Models.OrderProductItem;
import com.example.demo.Models.OrderProductItemId;
import com.example.demo.Models.Product;
import com.example.demo.Models.ProductList;
import com.example.demo.Models.ProductListItem;
import com.example.demo.Models.Store;
import com.example.demo.Models.User;
import com.example.demo.Models.UserAddress;
import com.example.demo.Repository.AddressRepository;
import com.example.demo.Repository.CartListRepository;
import com.example.demo.Repository.CategoryRepository;
import com.example.demo.Repository.OrderProductItemRepository;
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
public class OrderProductListRepositoryTest {
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
    private OrderProductItemRepository rep;

    @Autowired
    private TestEntityManager entityManager;


@Test
    void testWhenCreateOrderProductItemAndFindById_thenReturnSameOrderProductItem() {
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

        Set<Product> x = new HashSet();
        Category cat = new Category("Vegetais", false, x);
        
        Set<OrderProductItem> orderProductItem1= new HashSet();
        Set<ProductListItem> productListItems1= new HashSet();
        Product product = new Product("Pilhas", 5.1, "leve", true, productListItems1, orderProductItem1, cat);
        x.add(product);
        
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

        Set<Product> x = new HashSet();
        Category cat = new Category("Vegetais", false, x);
        
        Set<OrderProductItem> orderProductItem1= new HashSet();
        Set<ProductListItem> productListItems1= new HashSet();
        Set<OrderProductItem> orderProductItem12= new HashSet();
        Set<ProductListItem> productListItems12= new HashSet();

        Product product = new Product("Pilhas", 5.1, "leve", true, productListItems1, orderProductItem1, cat);
        Product product2 = new Product("Pilhas reciclaveis", 12, "leve", true, productListItems12, orderProductItem12, cat);
       
       
        x.add(product);
        x.add(product2);
        
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

    

}
