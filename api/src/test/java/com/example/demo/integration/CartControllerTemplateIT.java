package com.example.demo.integration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.example.demo.dto.LoginDto;
import com.example.demo.models.Address;
import com.example.demo.models.User;
import com.example.demo.models.UserAddress;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.UserAddressRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.AuthTokenFilter;
import com.example.demo.security.JwtUtils;


import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class CartControllerTemplateIT {
    @Container
    public static MySQLContainer<?> container = new MySQLContainer<>("mysql");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.username", container::getUsername);
    }


    @LocalServerPort
    int randomServerPort;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository adressRepository;

    @Autowired
    private UserAddressRepository uaRepository;

    @Autowired
    private JwtUtils jwtRepo;

    @Autowired
    private SecurityContextHolder securityContextHolder;
    @Autowired
    private AuthTokenFilter authToken;

    User user;
    Address address;    
    String token;
    UserAddress ua;

    @BeforeEach
    public void setUp(){
        Address address2 = new Address("Portugal", "1201-222", "Aveiro", "Rua das Estia");
       
        //String token= jwtRepo.generateJwtToken(1);
        //LOGIN
        User user= new User("alex20002011@gmail.com", "Alexandre", "pass",LocalDate.of(2000, 06, 28), "910123433", false, false);

        this.user= userRepository.saveAndFlush(user);
        Map<String, String> request = new HashMap<>();
        request.put("email", "alex20002011@gmail.com");
        request.put("password", "pass");
        ResponseEntity<Map> response = testRestTemplate.postForEntity("http://localhost:" + randomServerPort + "/login", request, Map.class);
        this.token = response.getBody().get("token").toString();
        //FINAL LOGIN

        this.address = adressRepository.saveAndFlush(address2);
        UserAddress ua= new UserAddress(user, address2);
        this.ua=uaRepository.saveAndFlush(ua);
    }

    @AfterEach
    public void resetDb() {
        uaRepository.deleteAll();
        uaRepository.flush();
        userRepository.deleteAll();
        userRepository.flush();
        adressRepository.deleteAll();
        adressRepository.flush();
    }

}
