package com.example.demo.repository;

import javax.persistence.PersistenceException;

import com.example.demo.models.CartList;
import com.example.demo.models.Category;
import com.example.demo.models.Product;
import com.example.demo.models.ProductList;
import com.example.demo.models.ProductListItem;
import com.example.demo.models.User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
public class CartListRepositoryTest {
    @Container
    public static MySQLContainer<?> container = new MySQLContainer<>("mysql");

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
        User user = new User("alex200020011@gmail.com", "Serras", "aaaaa", LocalDate.of(2000, 5, 28), "911912912", false, true);
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

    @Test
    void testGivenMultipleCartLists_WhenFindLastByUser_thenReturnLastUserCartList() {
        User user1 = new User("alex200020011@gmail.com", "Serras", "aaaaa", LocalDate.of(2000, 5, 28), "911912912", false, true);
        User user2 = new User("serras200020011@gmail.com", "Alexandre", "aaaaa", LocalDate.of(2000, 5, 28), "911912912", false, true);
        
        ProductList list1 = new ProductList(user1);
        ProductList list2 = new ProductList(user1);
        ProductList list3 = new ProductList(user2);

        Category category1 = new Category("Vegetais", true);
        Product product1 = new Product("Banana", 2.21f, "Não é vegetal mas come-se", true, category1);
        ProductListItem item1 = new ProductListItem(3, list1, product1);

        CartList cartList1 = new CartList(list1);
        CartList cartList2 = new CartList(list2);
        CartList cartList3 = new CartList(list3);

        entityManager.persistAndFlush(user1);
        entityManager.persistAndFlush(user2);

        entityManager.persistAndFlush(list1);
        entityManager.persistAndFlush(list2);
        entityManager.persistAndFlush(list3);

        entityManager.persistAndFlush(category1);
        entityManager.persistAndFlush(product1);
        entityManager.persistAndFlush(item1);

        entityManager.persistAndFlush(cartList1);

        CartList lastCart = rep.findFirstByProductListUserOrderByIdDesc(user1);
        assertEquals(cartList1.getProductList().getId(), lastCart.getProductList().getId());

        entityManager.persistAndFlush(cartList2);

        lastCart = rep.findFirstByProductListUserOrderByIdDesc(user1);
        assertEquals(cartList2.getProductList().getId(), lastCart.getProductList().getId());

        entityManager.persistAndFlush(cartList3);

        lastCart = rep.findFirstByProductListUserOrderByIdDesc(user1);
        assertEquals(cartList2.getProductList().getId(), lastCart.getProductList().getId());
    }

    @Test
    void testGivenAddressAndFindByAll_thenReturnSameAddress() {
        CartList x = new CartList();
        CartList x2 = new CartList();

        ProductList list = new ProductList();
        ProductList list2 = new ProductList();

        User user = new User("alex200020011@gmail.com", "Serras", "aaaaa", LocalDate.of(2000, 5, 28), "911912912", false, true);
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
