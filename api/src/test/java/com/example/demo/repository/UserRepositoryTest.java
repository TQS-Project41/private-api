package com.example.demo.repository;

import javax.validation.ConstraintViolationException;

import com.example.demo.models.User;
import com.example.demo.repository.UserRepository;

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
public class UserRepositoryTest {
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
    private UserRepository rep;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void testWhenCreateCartListAndFindById_thenReturnSameCartList() {        
        User user = new User("alex200020011@gmail.com", "Serras", "aaaaa", new Date(2000, 5, 28), "911912912", false, true);
        entityManager.persistAndFlush(user);
        Optional<User> res = rep.findById(user.getId());
        assertThat(res).isPresent().contains(user);
    }

    @Test
    void testWhenFindByInvalidId_thenReturnNull() {
        Optional<User> res = rep.findById(-1L);
        assertThat(res).isNotPresent();
    }
    /* ------------------------------------------------- *
     * FIND TESTS                                  *
     * ------------------------------------------------- *
     */

    @Test
    void testGivenAddressAndFindByAll_thenReturnSameAddress() {
        
        User user = new User("alex200020011@gmail.com", "Serras", "aaaaa", new Date(2000, 5, 28), "911912912", false, true);
        entityManager.persistAndFlush(user);
        
        User user1 = new User("alex20002001sdad1@gmail.com", "Serrdas", "aaasadaa", new Date(2000, 5, 28), "911912912", false, true);
        entityManager.persistAndFlush(user1);





        List<User> all = rep.findAll();

        assertThat(all).isNotNull();
        assertThat(all)
                .hasSize(2)
                .extracting(User::getEmail)
                .contains(user.getEmail(), user1.getEmail());
       
    }

    @Test
    void testGivenNoCartList_whenFindAll_thenReturnEmpty() {
        List<User> all = rep.findAll();
        assertThat(all).isNotNull().isEmpty();
    }

    @Test
    void testWhenCreateInvalidUserAndFindById_thenReturnException() {
        User  x = new User();
        assertThrows(ConstraintViolationException.class, () -> {
            entityManager.persistAndFlush(x);
        });
    }

   
}
