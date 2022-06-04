package com.example.demo.repository;

import javax.persistence.PersistenceException;

import com.example.demo.models.ProductList;
import com.example.demo.models.User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

import java.time.LocalDate;
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
public class ProductListRepositoryTest {
    @Container
    public static MySQLContainer<?> container = new MySQLContainer<>("mysql");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.username", container::getUsername);
    }

    @Autowired
    private ProductListRepository rep;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void testWhenCreateProductListAndFindById_thenReturnSameProductList() {
        User user = new User("alex200020011@gmail.com", "Serras", "aaaaa", LocalDate.of(2000, 5, 28), "911912912", false, true);
        entityManager.persistAndFlush(user);
        ProductList productListTmp = new ProductList();
        productListTmp.setUser(user);
        entityManager.persistAndFlush(user);
        entityManager.persistAndFlush(productListTmp);

       
        Optional<ProductList> res = rep.findById(productListTmp.getId());
        assertThat(res).isPresent().contains(productListTmp);
    }

    @Test
    void testWhenFindByInvalidId_thenReturnNull() {
        Optional<ProductList> res = rep.findById(-1L);
        assertThat(res).isNotPresent();
    }
    /* ------------------------------------------------- *
     * FIND TESTS                                  *
     * ------------------------------------------------- *
     */

    @Test
    void testGivenProductListItemAndFindByAll_thenReturnSameProductListItem() {
        User user = new User("alex200020011@gmail.com", "Serras", "aaaaa", LocalDate.of(2000, 5, 28), "911912912", false, true);
        entityManager.persistAndFlush(user);
        ProductList productListTmp = new ProductList();
        productListTmp.setUser(user);
        entityManager.persistAndFlush(productListTmp);

        User user1 = new User("ada@gmail.com", "Ser1111ras", "aaaaa", LocalDate.of(2000, 5, 28), "911912912", false, true);
        entityManager.persistAndFlush(user1);
        ProductList productListTmp1 = new ProductList();
        productListTmp1.setUser(user1);
        entityManager.persistAndFlush(productListTmp1);



        List<ProductList> all = rep.findAll();

        assertThat(all).isNotNull();
        assertThat(all)
                .hasSize(2)
                .extracting(ProductList::getUser)
                .contains(productListTmp.getUser(), productListTmp1.getUser());
       
    }

    @Test
    void testGivenNoProductList_whenFindAll_thenReturnEmpty() {
        List<ProductList> all = rep.findAll();
        assertThat(all).isNotNull().isEmpty();
    }

    @Test
    void testWhenCreateInvalidProductList_thenReturnException() {
        ProductList  x = new ProductList();
        assertThrows(PersistenceException.class, () -> {
            entityManager.persistAndFlush(x);
        });
    }

}
