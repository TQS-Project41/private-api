package com.example.demo.repository;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;

import com.example.demo.Models.Address;
import com.example.demo.Models.CartList;
import com.example.demo.Models.Category;
import com.example.demo.Models.Product;
import com.example.demo.Models.ProductList;
import com.example.demo.Models.User;
import com.example.demo.Models.UserAddress;
import com.example.demo.Repository.AddressRepository;
import com.example.demo.Repository.CartListRepository;
import com.example.demo.Repository.CategoryRepository;
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
public class CategoryRepositoryTest {
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
    private CategoryRepository rep;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void testWhenCreateCategoryAndFindById_thenReturnSameCategory() {
        Set<Product> x = new HashSet();
        Category cat = new Category("Vegetais", false, x);
        
        
        entityManager.persistAndFlush(cat);
        Optional<Category> res = rep.findById(cat.getId());
        assertThat(res).isPresent().contains(cat);
    }

    @Test
    void testWhenFindByInvalidId_thenReturnNull() {
        Optional<Category> res = rep.findById(-1L);
        assertThat(res).isNotPresent();
    }
    /* ------------------------------------------------- *
     * FIND TESTS                                  *
     * ------------------------------------------------- *
     */

    @Test
    void testGivenAddressAndFindByAll_thenReturnSameAddress() {
        Set<Product> x = new HashSet();
        Set<Product> y = new HashSet();

        Category cat = new Category("Vegetais", false, x);
        Category cat2 = new Category("Fruta", false, y);
        entityManager.persistAndFlush(cat);
        entityManager.persistAndFlush(cat2);
        List<Category> all = rep.findAll();

        assertThat(all).isNotNull();
        assertThat(all)
                .hasSize(2)
                .extracting(Category::getName)
                .contains(cat.getName(), cat2.getName());
       
    }

    @Test
    void testGivenNoCategory_whenFindAll_thenReturnEmpty() {
        List<Category> all = rep.findAll();
        assertThat(all).isNotNull().isEmpty();
    }

    @Test
    void testWhenCreateInvalidCategory_thenReturnException() {
        Category  x = new Category();
        assertThrows(ConstraintViolationException.class, () -> {
            entityManager.persistAndFlush(x);
        });
    }
}
