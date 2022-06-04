package com.example.demo.repository;

import javax.persistence.PersistenceException;

import com.example.demo.models.ProductList;
import com.example.demo.models.SavedList;
import com.example.demo.models.User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

import java.time.LocalDate;
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
public class SavedListRepositoryTest {
    @Container
    public static MySQLContainer<?> container = new MySQLContainer<>("mysql");

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
        User user = new User("alex200020011@gmail.com", "Serras", "aaaaa", LocalDate.of(2000, 5, 28), "911912912", false, true);
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

        User user = new User("alex200020011@gmail.com", "Serras", "aaaaa", LocalDate.of(2000, 5, 28), "911912912", false, true);
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

    @Test
    void testGivenMultipleOrders_whenFindingByUser_returnOnlyHisOrders() {
        User user1 = new User("pedro.dld@ua.pt", "Pedro Duarte", "password", LocalDate.of(2001, 11, 5), "249 311 804", true, true);
        User user2 = new User("alexandreserras@ua.pt", "Alexandre Serras", "password", LocalDate.of(2001, 9, 12), "249 321 116", true, true);

        ProductList productList = new ProductList(user1);
        SavedList list = new SavedList(productList, "Cenas");

        entityManager.persistAndFlush(user1);
        entityManager.persistAndFlush(user2);

        entityManager.persistAndFlush(productList);
        entityManager.persistAndFlush(list);

        assertThat(rep.findByProductListUser(user1, Pageable.unpaged()).getNumberOfElements()).isEqualTo(1);
        assertThat(rep.findByProductListUser(user2, Pageable.unpaged()).getNumberOfElements()).isZero();
    }
}
