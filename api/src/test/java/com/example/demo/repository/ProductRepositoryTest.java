package com.example.demo.repository;

import javax.validation.ConstraintViolationException;

import com.example.demo.models.Category;
import com.example.demo.models.Product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Pageable;
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
    public static MySQLContainer<?> container = new MySQLContainer<>("mysql");

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

    @Test
    void whenFilteringByNameAndMinPrice_thenReturnFilteredList () {

        Category category1 = new Category("Vegetais", true);
        Category category2 = new Category("Fruta", true);

        Product product1 = new Product("Alface", 23.12f, "Gosto", true, category1);
        Product product2 = new Product("Cenoura", 9.11f, "Laranja", true, category1);
        Product product3 = new Product("Tomate", 12.32f, "Avermelhado", true, category1);
        Product product4 = new Product("Banana", 3.12f, "Gosto", true, category2);
        Product product5 = new Product("Pêra Rocha", 3.20f, "Bom e barato", true, category2);
        Product product6 = new Product("Maçã de Alcobaça", 5.99f, "Goat", true, category2);

        entityManager.persistAndFlush(category1);
        entityManager.persistAndFlush(category2);

        entityManager.persistAndFlush(product1);
        entityManager.persistAndFlush(product2);
        entityManager.persistAndFlush(product3);
        entityManager.persistAndFlush(product4);
        entityManager.persistAndFlush(product5);
        entityManager.persistAndFlush(product6);

        assertThat(rep.findByNameContainsAndPriceGreaterThan("", 6, Pageable.unpaged())).containsExactly(product1, product2, product3);
        assertThat(rep.findByNameContainsAndPriceGreaterThan("Al", 5, Pageable.unpaged())).containsExactly(product1, product6);

    }

    @Test
    void whenFilteringByNameAndMinPriceAndMaxPrice_thenReturnFilteredList () {

        Category category1 = new Category("Vegetais", true);
        Category category2 = new Category("Fruta", true);

        Product product1 = new Product("Alface", 23.12f, "Gosto", true, category1);
        Product product2 = new Product("Cenoura", 9.11f, "Laranja", true, category1);
        Product product3 = new Product("Tomate", 12.32f, "Avermelhado", true, category1);
        Product product4 = new Product("Banana", 3.12f, "Gosto", true, category2);
        Product product5 = new Product("Pêra Rocha", 3.20f, "Bom e barato", true, category2);
        Product product6 = new Product("Maçã de Alcobaça", 5.99f, "Goat", true, category2);

        entityManager.persistAndFlush(category1);
        entityManager.persistAndFlush(category2);

        entityManager.persistAndFlush(product1);
        entityManager.persistAndFlush(product2);
        entityManager.persistAndFlush(product3);
        entityManager.persistAndFlush(product4);
        entityManager.persistAndFlush(product5);
        entityManager.persistAndFlush(product6);

        assertThat(rep.findByNameContainsAndPriceBetween("", 6, 20, Pageable.unpaged())).containsExactly(product2, product3);
        assertThat(rep.findByNameContainsAndPriceBetween("Al", 5, 20, Pageable.unpaged())).containsExactly(product6);

    }

    @Test
    void whenFilteringByCategoryAndNameAndMinPrice_thenReturnFilteredList () {

        Category category1 = new Category("Vegetais", true);
        Category category2 = new Category("Fruta", true);

        Product product1 = new Product("Alface", 23.12f, "Gosto", true, category1);
        Product product2 = new Product("Cenoura", 9.11f, "Laranja", true, category1);
        Product product3 = new Product("Tomate", 12.32f, "Avermelhado", true, category1);
        Product product4 = new Product("Banana", 3.12f, "Gosto", true, category2);
        Product product5 = new Product("Pêra Rocha", 3.20f, "Bom e barato", true, category2);
        Product product6 = new Product("Maçã de Alcobaça", 5.99f, "Goat", true, category2);

        entityManager.persistAndFlush(category1);
        entityManager.persistAndFlush(category2);

        entityManager.persistAndFlush(product1);
        entityManager.persistAndFlush(product2);
        entityManager.persistAndFlush(product3);
        entityManager.persistAndFlush(product4);
        entityManager.persistAndFlush(product5);
        entityManager.persistAndFlush(product6);

        assertThat(rep.findByCategoryIdAndNameContainsAndPriceGreaterThan(category2.getId(), "", 4, Pageable.unpaged())).containsExactly(product6);
        assertThat(rep.findByCategoryIdAndNameContainsAndPriceGreaterThan(category1.getId(), "Al", 5, Pageable.unpaged())).containsExactly(product1);

    }

    @Test
    void whenFilteringByCategoryAndNameAndMinPriceAndMaxPrice_thenReturnFilteredList () {

        Category category1 = new Category("Vegetais", true);
        Category category2 = new Category("Fruta", true);

        Product product1 = new Product("Alface", 23.12f, "Gosto", true, category1);
        Product product2 = new Product("Cenoura", 9.11f, "Laranja", true, category1);
        Product product3 = new Product("Tomate", 12.32f, "Avermelhado", true, category1);
        Product product4 = new Product("Banana", 3.12f, "Gosto", true, category2);
        Product product5 = new Product("Pêra Rocha", 3.20f, "Bom e barato", true, category2);
        Product product6 = new Product("Maçã de Alcobaça", 5.99f, "Goat", true, category2);

        entityManager.persistAndFlush(category1);
        entityManager.persistAndFlush(category2);

        entityManager.persistAndFlush(product1);
        entityManager.persistAndFlush(product2);
        entityManager.persistAndFlush(product3);
        entityManager.persistAndFlush(product4);
        entityManager.persistAndFlush(product5);
        entityManager.persistAndFlush(product6);


        assertThat(rep.findByCategoryIdAndNameContainsAndPriceBetween(category1.getId(), "", 0, 15, Pageable.unpaged())).containsExactly(product2, product3);
        assertThat(rep.findByCategoryIdAndNameContainsAndPriceBetween(category1.getId(), "Al", 5, 20, Pageable.unpaged())).isEmpty();

    }

}
