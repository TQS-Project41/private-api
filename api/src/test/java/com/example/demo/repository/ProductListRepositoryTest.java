package com.example.demo.repository;
import com.example.demo.Repository.ProductListItemRepository;
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
import com.example.demo.Models.ProductListItemId;
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
public class ProductListRepositoryTest {
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
    private ProductListRepository rep;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void testWhenCreateProductListAndFindById_thenReturnSameProductList() {
        Set<ProductList> productList = new HashSet<>();
        Set<UserAddress> userAddress=new HashSet<>();
        User user = new User("alex200020011@gmail.com", "Serras", "aaaaa", new Date(2000, 5, 28), "911912912", false, true, productList, userAddress);
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
        Set<ProductList> productList = new HashSet<>();
        Set<UserAddress> userAddress=new HashSet<>();
        User user = new User("alex200020011@gmail.com", "Serras", "aaaaa", new Date(2000, 5, 28), "911912912", false, true, productList, userAddress);
        entityManager.persistAndFlush(user);
        ProductList productListTmp = new ProductList();
        productListTmp.setUser(user);
        entityManager.persistAndFlush(productListTmp);

        Set<ProductList> productList1 = new HashSet<>();
        Set<UserAddress> userAddress1=new HashSet<>();
        User user1 = new User("ada@gmail.com", "Ser1111ras", "aaaaa", new Date(2000, 5, 28), "911912912", false, true, productList1, userAddress1);
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
