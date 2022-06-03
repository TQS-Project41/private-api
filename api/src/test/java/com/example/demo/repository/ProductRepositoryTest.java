package com.example.demo.repository;

import javax.validation.ConstraintViolationException;

import com.example.demo.models.Category;
import com.example.demo.models.Product;
import com.example.demo.repository.ProductRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

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
public class ProductRepositoryTest {
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
    private ProductRepository rep;

    @Autowired
    private TestEntityManager entityManager;



    @Test
    void testWhenCreateOrderProductItemAndFindById_thenReturnSameOrderProductItem() {
        Category cat = new Category("Vegetais", false);
        
        Product product = new Product("Pilhas", 5.1f, "leve", true, cat);
        
        entityManager.persistAndFlush(cat);
        entityManager.persistAndFlush(product);
       
        Optional<Product> res = rep.findById(product.getId());
        assertThat(res).isPresent().contains(product);
    }
    @Test
    void testWhenFindByInvalidId_thenReturnNull() {
        Optional<Product> res = rep.findById(-1L);
        assertThat(res).isNotPresent();
    }
    /* ------------------------------------------------- *
     * FIND TESTS                                  *
     * ------------------------------------------------- *
     */

    @Test
    void testGivenAddressAndFindByAll_thenReturnSameAddress() {
        Category cat = new Category("Vegetais", false);

        Product product = new Product("Pilhas", 5.1f, "leve", true, cat);
        Product product2 = new Product("Pilhas reciclaveis", 12, "leve", true, cat);
        
        entityManager.persistAndFlush(cat);
        entityManager.persistAndFlush(product);
        entityManager.persistAndFlush(product2);

        List<Product> all = rep.findAll();

        assertThat(all).isNotNull();
        assertThat(all)
                .hasSize(2)
                .extracting(Product::getName)
                .contains(product.getName(), product2.getName());
       
    }

    @Test
    void testGivenNoProduct_whenFindAll_thenReturnEmpty() {
        List<Product> all = rep.findAll();
        assertThat(all).isNotNull().isEmpty();
    }

    @Test
    void testWhenCreateInvalidProduct_thenReturnException() {
        Product  x = new Product();
        assertThrows(ConstraintViolationException.class, () -> {
            entityManager.persistAndFlush(x);
        });
    }

}
