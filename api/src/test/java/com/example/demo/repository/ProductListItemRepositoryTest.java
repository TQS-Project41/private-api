package com.example.demo.repository;

import com.example.demo.models.Address;
import com.example.demo.models.Category;
import com.example.demo.models.OrderList;
import com.example.demo.models.Product;
import com.example.demo.models.ProductList;
import com.example.demo.models.ProductListItem;
import com.example.demo.models.ProductListItemId;
import com.example.demo.models.Store;
import com.example.demo.models.User;
import com.example.demo.models.UserAddress;
import com.example.demo.repository.ProductListItemRepository;

import javax.persistence.PersistenceException;

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
public class ProductListItemRepositoryTest {
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
    private ProductListItemRepository rep;

    @Autowired
    private TestEntityManager entityManager;


    @Test
    void testWhenCreateOrderProductItemAndFindById_thenReturnSameOrderProductItem() {
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

        Category cat = new Category("Vegetais", false);
        
        Product product = new Product("Pilhas", 5.1f, "leve", true, cat);
        
        entityManager.persistAndFlush(cat);
        entityManager.persistAndFlush(product);

        ProductListItem order = new ProductListItem(8, productListTmp, product);

        entityManager.persistAndFlush(order);

       
        Optional<ProductListItem> res = rep.findById(order.getId());
        assertThat(res).isPresent().contains(order);
    }





    @Test
    void testWhenFindByInvalidId_thenReturnNull() {
        Optional<ProductListItem> res = rep.findById(new ProductListItemId(-1L, -1L));
        assertThat(res).isNotPresent();
    }
    /* ------------------------------------------------- *
     * FIND TESTS                                  *
     * ------------------------------------------------- *
     */


    @Test
    void testGivenProductListItemAndFindByAll_thenReturnSameProductListItem() {
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

        Category cat = new Category("Vegetais", false);

        Product product = new Product("Pilhas", 5.1f, "leve", true, cat);
        Product product2 = new Product("Pilhas reciclaveis", 12, "leve", true, cat);
        
        entityManager.persistAndFlush(cat);
        entityManager.persistAndFlush(product);
        entityManager.persistAndFlush(product2);

        ProductListItem order = new ProductListItem(8, productListTmp, product);

        entityManager.persistAndFlush(order);

        ProductListItem order2 = new ProductListItem(5, productListTmp, product2);

        entityManager.persistAndFlush(order2);




        List<ProductListItem> all = rep.findAll();

        assertThat(all).isNotNull();
        assertThat(all)
                .hasSize(2)
                .extracting(ProductListItem::getAmount)
                .contains(order.getAmount(), order2.getAmount());
       
    }

    @Test
    void testGivenNoProductListItem_whenFindAll_thenReturnEmpty() {
        List<ProductListItem> all = rep.findAll();
        assertThat(all).isNotNull().isEmpty();
    }

    @Test
    void testWhenCreateInvalidProductListItem_thenReturnException() {
        ProductListItem  x = new ProductListItem();
        assertThrows(PersistenceException.class, () -> {
            entityManager.persistAndFlush(x);
        });
    }
}
