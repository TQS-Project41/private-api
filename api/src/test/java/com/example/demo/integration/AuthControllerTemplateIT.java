package com.example.demo.integration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.example.demo.dto.LoginDto;
import com.example.demo.models.User;
import com.example.demo.repository.UserRepository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class AuthControllerTemplateIT {
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
    @Test
    public void testLoginWhenWrongPassword_thenNotFound() {

        User u = new User("pedro.dld@ua.pt", "name", "pass11word", LocalDate.of(2000, 05, 15), "911123123", false, false);
        userRepository.saveAndFlush(u);
        Map<String, String> request = new HashMap<>();
        request.put("email", "pedro.dld@ua.pt");
        request.put("password", "password");
        ResponseEntity<String> response =
                testRestTemplate.postForEntity(getBaseUrl()+"/login", request, String.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));
        userRepository.delete(u);
        userRepository.flush();

    }

    @Test
    public void testLoginWhenValidUser_thenUser() {

        User u = new User("pedro.dld@ua.pt", "name", "password", LocalDate.of(2000, 05, 15), "911123123", false, false);
        userRepository.saveAndFlush(u);
        Map<String, String> request = new HashMap<>();
        request.put("email", "pedro.dld@ua.pt");
        request.put("password", "password");
        ResponseEntity<Map> response =
                testRestTemplate.postForEntity(getBaseUrl()+"/login", request, Map.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        userRepository.delete(u);
        userRepository.flush();

    }

    @Test
    public void testInvalidRegister_thenBadArgs() {

        Map<String, String> body = new HashMap<>();
        body.put("email", "pedro.dld@ua.pt");
        body.put("name", "Pedro");
        body.put("password", "password");
        body.put("phoneNumber", "249 123 019");
        ResponseEntity<String> response =
                testRestTemplate.postForEntity(getBaseUrl()+"/register", body, String.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));


    }


    @Test
    public void testRegister_thenCreated() {

        Map<String, String> body = new HashMap<>();
        body.put("email", "pedro.dld@ua.pt");
        body.put("name", "Pedro");
        body.put("password", "password");
        body.put("birthday", "10-02-2001");
        body.put("phoneNumber", "249 123 019");
        ResponseEntity<User> response =
                testRestTemplate.postForEntity(getBaseUrl()+"/register", body, User.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));
        assertThat(response.getBody().getEmail(), equalTo("pedro.dld@ua.pt"));


    }

    

    @Test
    public void testLoginWhenInvalidEmail_thenNOT_FOUND() {
        Map<String, String> request = new HashMap<>();
        request.put("email", "pedrsadasdsadsdso.dld@ua.pt");
        request.put("password", "password");
        ResponseEntity<String> response =
                testRestTemplate.postForEntity(getBaseUrl()+"/login", request, String.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));
    }

    @Test
    public void testLoginWhenNoPassword_thenBadReques() {
        Map<String, String> request = new HashMap<>();
        request.put("email", "pedro.dld@ua.pt");
        ResponseEntity<String> response =
                testRestTemplate.postForEntity(getBaseUrl()+"/login", request, String.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
    }

    //fazer um bad parameters para login e registo
    private String getBaseUrl() {
        return "http://localhost:"+randomServerPort;
    }
}
