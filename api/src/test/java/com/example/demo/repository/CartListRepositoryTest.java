package com.example.demo.repository;

import javax.persistence.PersistenceException;

import com.example.demo.models.CartList;
import com.example.demo.models.ProductList;
import com.example.demo.models.User;
import com.example.demo.repository.CartListRepository;

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
public class CartListRepositoryTest {
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
    private CartListRepository rep;

    

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void testWhenCreateCartListAndFindById_thenReturnSameCartList() {
        CartList x = new CartList();
        ProductList list = new ProductList();
        User user = new User("alex200020011@gmail.com", "Serras", "aaaaa", new Date(2000, 5, 28), "911912912", false, true);
        entityManager.persistAndFlush(user);
        list.setUser(user);
        entityManager.persistAndFlush(list);
        x.setProductList(list);
        entityManager.persistAndFlush(x);
        Optional<CartList> res = rep.findById(x.getProductList().getId());
        assertThat(res).isPresent().contains(x);
    }

    @Test
    void testWhenFindByInvalidId_thenReturnNull() {
        Optional<CartList> res = rep.findById(-1L);
        assertThat(res).isNotPresent();
    }
    /* ------------------------------------------------- *
     * FIND TESTS                                  *
     * ------------------------------------------------- *
     */

    @Test
    void testGivenAddressAndFindByAll_thenReturnSameAddress() {
        CartList x = new CartList();
        CartList x2 = new CartList();

        ProductList list = new ProductList();
        ProductList list2 = new ProductList();

        User user = new User("alex200020011@gmail.com", "Serras", "aaaaa", new Date(2000, 5, 28), "911912912", false, true);
        entityManager.persistAndFlush(user);
        list.setUser(user);
        list2.setUser(user);
        entityManager.persistAndFlush(list);
        x.setProductList(list);
        x2.setProductList(list2);
        entityManager.persistAndFlush(x);
        entityManager.persistAndFlush(x2);





        List<CartList> all = rep.findAll();

        assertThat(all).isNotNull();
        assertThat(all)
                .hasSize(2)
                .extracting(CartList::getProductList)
                .contains(x.getProductList(), x2.getProductList());
       
    }

    @Test
    void testGivenNoCartList_whenFindAll_thenReturnEmpty() {
        List<CartList> all = rep.findAll();
        assertThat(all).isNotNull().isEmpty();
    }

    @Test
    void testWhenCreateInvalidCartListAndFindById_thenReturnException() {
        CartList  x = new CartList();
        assertThrows(PersistenceException.class, () -> {
            entityManager.persistAndFlush(x);
        });
    }
}
