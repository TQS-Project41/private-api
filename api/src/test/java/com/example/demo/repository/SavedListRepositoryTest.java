package com.example.demo.repository;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;

import com.example.demo.Models.Address;
import com.example.demo.Models.CartList;
import com.example.demo.Models.ProductList;
import com.example.demo.Models.SavedList;
import com.example.demo.Models.User;
import com.example.demo.Models.UserAddress;
import com.example.demo.Repository.AddressRepository;
import com.example.demo.Repository.CartListRepository;
import com.example.demo.Repository.ProductListRepository;
import com.example.demo.Repository.SavedListRepository;
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
public class SavedListRepositoryTest {
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
    private SavedListRepository rep;

    

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void testWhenCreateSavedListAndFindById_thenReturnSameSavedList() {
        SavedList x = new SavedList();
        ProductList list = new ProductList();
        Set<ProductList> productList = new HashSet<>();
        Set<UserAddress> userAddress=new HashSet<>();
        User user = new User("alex200020011@gmail.com", "Serras", "aaaaa", new Date(2000, 5, 28), "911912912", false, true, productList, userAddress);
        entityManager.persistAndFlush(user);
        list.setUser(user);
        entityManager.persistAndFlush(list);
        x.setProductList(list);
        entityManager.persistAndFlush(x);


        Optional<SavedList> res = rep.findById(x.getId());
        assertThat(res).isPresent().contains(x);
    }

    @Test
    void testWhenFindByInvalidId_thenReturnNull() {
        Optional<SavedList> res = rep.findById(-1L);
        assertThat(res).isNotPresent();
    }
    /* ------------------------------------------------- *
     * FIND TESTS                                  *
     * ------------------------------------------------- *
     */

    @Test
    void testGivenAddressAndFindByAll_thenReturnSameAddress() {
        SavedList x = new SavedList();
        SavedList x2 = new SavedList();
        x2.setName("aaaa");
        ProductList list = new ProductList();
        ProductList list2 = new ProductList();

        Set<ProductList> productList = new HashSet<>();
        Set<UserAddress> userAddress=new HashSet<>();
        User user = new User("alex200020011@gmail.com", "Serras", "aaaaa", new Date(2000, 5, 28), "911912912", false, true, productList, userAddress);
        entityManager.persistAndFlush(user);
        list.setUser(user);
        list2.setUser(user);
        entityManager.persistAndFlush(list);
        x.setProductList(list);
        x2.setProductList(list2);
        entityManager.persistAndFlush(x);
        entityManager.persistAndFlush(x2);





        List<SavedList> all = rep.findAll();

        assertThat(all).isNotNull();
        assertThat(all)
                .hasSize(2)
                .extracting(SavedList::getName)
                .contains(x.getName(), x2.getName());
       
    }

    @Test
    void testGivenNoSavedList_whenFindAll_thenReturnEmpty() {
        List<SavedList> all = rep.findAll();
        assertThat(all).isNotNull().isEmpty();
    }

    @Test
    void testWhenCreateInvalidSavedListAndFindById_thenReturnException() {
        SavedList  x = new SavedList();
        assertThrows(PersistenceException.class, () -> {
            entityManager.persistAndFlush(x);
        });
    }
}
