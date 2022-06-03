package com.example.demo.repository;

import javax.validation.ConstraintViolationException;

import com.example.demo.models.Address;
import com.example.demo.models.Store;
import com.example.demo.repository.StoreRepository;

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
public class StoreRepositoryTest {
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
    private StoreRepository rep;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void testWhenCreateOrderProductItemAndFindById_thenReturnSameOrderProductItem() {

        Store store = new Store();
        store.setName("puma");
        Address address= new Address("Portugal", "1903-221", "Aveiro", "Rua das Pombas");
        store.setAddress(address);
        entityManager.persistAndFlush(store);

        Optional<Store> res = rep.findById(store.getId());
        assertThat(res).isPresent().contains(store);
    }
    @Test
    void testWhenFindByInvalidId_thenReturnNull() {
        Optional<Store> res = rep.findById(-1L);
        assertThat(res).isNotPresent();
    }
    /* ------------------------------------------------- *
     * FIND TESTS                                  *
     * ------------------------------------------------- *
     */

    @Test
    void testGivenAddressAndFindByAll_thenReturnSameAddress() {
        Store store = new Store();
        store.setName("puma");
        Address address= new Address("Portugal", "1903-221", "Aveiro", "Rua das Pombas");
        store.setAddress(address);
        entityManager.persistAndFlush(store);


        Store store1 = new Store();
        store1.setName("JOMA");
        Address address1= new Address("Portugal", "3903-221", "Santarem", "Rua das Estia");
        store1.setAddress(address1);
        entityManager.persistAndFlush(store1);
        


        List<Store> all = rep.findAll();

        assertThat(all).isNotNull();
        assertThat(all)
                .hasSize(2)
                .extracting(Store::getName)
                .contains(store.getName(), store1.getName());
       
    }

    @Test
    void testGivenNoStore_whenFindAll_thenReturnEmpty() {
        List<Store> all = rep.findAll();
        assertThat(all).isNotNull().isEmpty();
    }

    @Test
    void testWhenCreateInvalidStore_thenReturnException() {
        Store  x = new Store();
        assertThrows(ConstraintViolationException.class, () -> {
            entityManager.persistAndFlush(x);
        });
    }

}
