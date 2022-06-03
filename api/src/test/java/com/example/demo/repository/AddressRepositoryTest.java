package com.example.demo.repository;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import com.example.demo.models.Address;
import com.example.demo.repository.AddressRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

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
public class AddressRepositoryTest {
    
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
    private AddressRepository rep;

    @Autowired
    private TestEntityManager entityManager;
    
    @Test
    void testWhenCreateAddressAndFindById_thenReturnSameAddress() {
        Address x = new Address();
        x.setAddress("Rua da Estia");
        x.setCity("Aveiro");
        x.setCountry("Portugal");
        x.setZipcode("1023-221");
        entityManager.persistAndFlush(x);
        Optional<Address> res = rep.findById(x.getId());
        
        assertThat(res).isPresent().contains(x);
    }

    @Test
    void testWhenFindByInvalidId_thenReturnNull() {
        Optional<Address> res = rep.findById(-1L);
        assertThat(res).isNotPresent();
    }
    /* ------------------------------------------------- *
     * FIND TESTS                                  *
     * ------------------------------------------------- *
     */

    @Test
    void testGivenAddressAndFindByAll_thenReturnSameAddress() {
        Address x = new Address();
        x.setAddress("Rua da Estia");
        x.setCity("Aveiro");
        x.setCountry("Portugal");
        x.setZipcode("1023-221");
        Address x2 = new Address();
        x2.setAddress("Rua do Reis");
        x2.setCity("Ovar");
        x2.setCountry("Portugal");
        x2.setZipcode("1013-221");
        entityManager.persistAndFlush(x);
        entityManager.persistAndFlush(x2);


        List<Address> all = rep.findAll();

        assertThat(all).isNotNull();
        assertThat(all)
                .hasSize(2)
                .extracting(Address::getId)
                .contains(x.getId(), x2.getId());
        assertThat(all)
                .extracting(Address::getCity)
                .contains(x.getCity(), x2.getCity());
    }

    @Test
    void testGivenNoAddress_whenFindAll_thenReturnEmpty() {
        List<Address> all = rep.findAll();
        assertThat(all).isNotNull().isEmpty();
    }

    @Test
    void testWhenCreateInvalidAddressAndFindById_thenReturnException() {
        Address  x = new Address();
        assertThrows(ConstraintViolationException.class, () -> {
            entityManager.persistAndFlush(x);
        });
    }

}
