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
import com.example.demo.Models.UserAddressID;
import com.example.demo.Repository.AddressRepository;
import com.example.demo.Repository.CartListRepository;
import com.example.demo.Repository.CategoryRepository;
import com.example.demo.Repository.OrderProductItemRepository;
import com.example.demo.Repository.ProductListRepository;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.Repository.UserAddressRepository;
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
public class UserAddressRepositoryTest {
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
    private UserAddressRepository rep;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void testWhenCreateOrderProductItemAndFindById_thenReturnSameOrderProductItem() {

        Address address= new Address("Portugal", "1903-221", "Aveiro", "Rua das Pombas");
        entityManager.persistAndFlush(address);
        User user = new User("alex200020011@gmail.com", "Serras", "aaaaa", new Date(2000, 5, 28), "911912912", false, true);
        entityManager.persistAndFlush(user);


        UserAddress uA = new UserAddress(user, address);
        entityManager.persistAndFlush(uA);

        Optional<UserAddress> res = rep.findById(uA.getId());
        assertThat(res).isPresent().contains(uA);
    }
    @Test
    void testWhenFindByInvalidId_thenReturnNull() {
        Optional<UserAddress> res = rep.findById(new UserAddressID(-1L, -1L));
        assertThat(res).isNotPresent();
    }
    /* ------------------------------------------------- *
     * FIND TESTS                                  *
     * ------------------------------------------------- *
     */

    @Test
    void testGivenUserAddressAndFindByAll_thenReturnSameUserAddress() {
        Address address= new Address("Portugal", "1903-221", "Aveiro", "Rua das Pombas");
        entityManager.persistAndFlush(address);
        User user = new User("alex200020011@gmail.com", "Serras", "aaaaa", new Date(2000, 5, 28), "911912912", false, true);
        entityManager.persistAndFlush(user);


        UserAddress uA = new UserAddress(user, address);
        entityManager.persistAndFlush(uA);


        Address address1= new Address("Portugal", "1903-111", "Ovar", "Rua das Estia");
        entityManager.persistAndFlush(address1);
        User user1 = new User("aaa@gmail.com", "Serras", "aaaaa", new Date(2000, 5, 28), "912321321", false, true);
        entityManager.persistAndFlush(user1);


        UserAddress uA1 = new UserAddress(user1, address1);
        entityManager.persistAndFlush(uA1);




        List<UserAddress> all = rep.findAll();

        assertThat(all).isNotNull();
        assertThat(all)
                .hasSize(2)
                .extracting(UserAddress::getUser)
                .contains(uA.getUser(), uA1.getUser());
       
    }

    @Test
    void testGivenNoUserAddressList_whenFindAll_thenReturnEmpty() {
        List<UserAddress> all = rep.findAll();
        assertThat(all).isNotNull().isEmpty();
    }

    @Test
    void testWhenCreateInvalidUserAndFindById_thenReturnException() {
        UserAddress  x = new UserAddress();
        assertThrows(PersistenceException.class, () -> {
            entityManager.persistAndFlush(x);
        });
    }

}
