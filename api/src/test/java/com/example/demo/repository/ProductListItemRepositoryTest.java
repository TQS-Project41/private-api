package com.example.demo.repository;

import com.example.demo.models.Address;
import com.example.demo.models.Category;
import com.example.demo.models.OrderList;
import com.example.demo.models.Product;
import com.example.demo.models.ProductList;
import com.example.demo.models.ProductListItem;
import com.example.demo.models.ListItemId;
import com.example.demo.models.Store;
import com.example.demo.models.User;
import com.example.demo.models.UserAddress;

import javax.persistence.PersistenceException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

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
public class ProductListItemRepositoryTest {
    @Container
    public static MySQLContainer<?> container = new MySQLContainer<>("mysql");

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

        ProductListItem order = new ProductListItem(8, productListTmp, product);

        entityManager.persistAndFlush(order);

       
        Optional<ProductListItem> res = rep.findById(order.getId());
        assertThat(res).isPresent().contains(order);
    }

    @Test
    void testWhenFindByInvalidId_thenReturnNull() {
        Optional<ProductListItem> res = rep.findById(new ListItemId(-1L, -1L));
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

    @Test
    void givenMultipleLists_whenFindingItemsByListId_thenReturnOnlyItsItems() {
        float productPrice = 14.78f;
        Category category = new Category("Vegetais", true);
        Product product = new Product("Couve-Flor", productPrice, "Saboroso", true, category);

        User user = new User("pedro.dld@ua.pt", "Pedro Duarte", "password", LocalDate.of(2001, 11, 5), "249 311 804", true, true);
        Address address = new Address("country", "zipcode", "city", "address");
        Store store = new Store("Store", address);

        ProductList productList1 = new ProductList(user);
        ProductList productList2 = new ProductList(user);

        ProductListItem item = new ProductListItem(5, productList1, product);

        entityManager.persistAndFlush(category);
        entityManager.persistAndFlush(product);
        entityManager.persistAndFlush(user);
        entityManager.persistAndFlush(address);
        entityManager.persistAndFlush(store);
        entityManager.persistAndFlush(productList1);
        entityManager.persistAndFlush(productList2);
        entityManager.persistAndFlush(item);

        assertThat(rep.findByListId(productList1.getId())).hasSize(1);
        assertThat(rep.findByListId(productList2.getId())).isEmpty();
    }

    @Test
    void givenMultipleListsAndItems_whenFindingItemsByListItemAndProductId_thenReturnOnlyOneItem() {
        float productPrice = 14.78f;
        Category category = new Category("Vegetais", true);
        
        Product product1 = new Product("Couve-Flor", productPrice, "Saboroso", true, category);
        Product product2 = new Product("Alface", productPrice, "Nem tanto", true, category);

        User user = new User("pedro.dld@ua.pt", "Pedro Duarte", "password", LocalDate.of(2001, 11, 5), "249 311 804", true, true);
        Address address = new Address("country", "zipcode", "city", "address");
        Store store = new Store("Store", address);

        ProductList productList1 = new ProductList(user);
        ProductList productList2 = new ProductList(user);

        ProductListItem item1 = new ProductListItem(15, productList1, product1);
        ProductListItem item2 = new ProductListItem(1, productList1, product2);
        ProductListItem item3 = new ProductListItem(3, productList2, product1);

        entityManager.persistAndFlush(category);
        entityManager.persistAndFlush(product1);
        entityManager.persistAndFlush(product2);
        entityManager.persistAndFlush(user);
        entityManager.persistAndFlush(address);
        entityManager.persistAndFlush(store);
        entityManager.persistAndFlush(productList1);
        entityManager.persistAndFlush(productList2);
        entityManager.persistAndFlush(item1);
        entityManager.persistAndFlush(item2);
        entityManager.persistAndFlush(item3);

        assertThat(rep.findByListIdAndProductId(productList1.getId(), product1.getId()).get().getAmount()).isEqualTo(15);
        assertThat(rep.findByListIdAndProductId(productList1.getId(), product2.getId()).get().getAmount()).isEqualTo(1);
        assertThat(rep.findByListIdAndProductId(productList2.getId(), product1.getId()).get().getAmount()).isEqualTo(3);
    }
}
