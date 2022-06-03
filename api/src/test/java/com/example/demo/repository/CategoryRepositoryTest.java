package com.example.demo.repository;

import javax.validation.ConstraintViolationException;

import com.example.demo.models.Category;
import com.example.demo.repository.CategoryRepository;

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
        Category cat = new Category("Vegetais", false);
        
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
        Category cat = new Category("Vegetais", false);
        Category cat2 = new Category("Fruta", false);
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
