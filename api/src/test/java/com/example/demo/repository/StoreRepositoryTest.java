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
import com.example.demo.Repository.ProductRepository;
import com.example.demo.Repository.StoreRepository;
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
        Address address= new Address("Portugal", "1903-221", "Aveiro", "Rua das Pombas", store);
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
        Address address= new Address("Portugal", "1903-221", "Aveiro", "Rua das Pombas", store);
        store.setAddress(address);
        entityManager.persistAndFlush(store);


        Store store1 = new Store();
        store1.setName("JOMA");
        Address address1= new Address("Portugal", "3903-221", "Santarem", "Rua das Estia", store1);
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
