package com.example.demo.repository;

import javax.persistence.PersistenceException;

import com.example.demo.models.Address;
import com.example.demo.models.User;
import com.example.demo.models.UserAddress;
import com.example.demo.repository.UserAddressRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

import java.util.Date;
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

        Optional<UserAddress> res = rep.findById(address.getId());
        assertThat(res).isPresent().contains(uA);
    }
    @Test
    void testWhenFindByInvalidId_thenReturnNull() {
        Optional<UserAddress> res = rep.findById(-1L);
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
