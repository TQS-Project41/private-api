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
import com.example.demo.service.AddressService;
import com.example.demo.service.UserService;

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
public class AdressControllerTemplateIT {
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
    private AuthTokenFilter authTokenFilter;
    
    @Autowired
    private AddressRepository adressRepository;

    @Autowired
    private UserAddressRepository uaRepository;

    User user;
    Address address;   
    String token;
    UserAddress ua;

    @BeforeEach
    public void setUp(){
        //LOGIN
        User user= new User("alex20002011@gmail.com", "Alexandre", "pass",LocalDate.of(2000, 06, 28), "910123433", false, false);

        this.user= userRepository.saveAndFlush(user);
        Map<String, String> request = new HashMap<>();
        request.put("email", "alex20002011@gmail.com");
        request.put("password", "pass");
        ResponseEntity<Map> response = testRestTemplate.postForEntity("http://localhost:" + randomServerPort + "/login", request, Map.class);
        this.token = response.getBody().get("token").toString();
        //FINAL LOGIN
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

    @Test
    void testGetAddressbyInvalidUser_thenReturnNotFound(){

        HttpHeaders headers= new HttpHeaders();
        headers.set("Authorization", "Bearer "+"");
        HttpEntity requestEntity = new HttpEntity<Object>(headers);
        ResponseEntity<List> response =
                testRestTemplate.exchange(getBaseUrl()+"/addresses", HttpMethod.GET, requestEntity, List.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.FORBIDDEN));
             
    }

    @Test
    void testPostInvalidAddress_thenReturnBadArgs(){

        HttpHeaders headers= new HttpHeaders();
        headers.set("Authorization", "Bearer "+this.token);
        HttpEntity requestEntity = new HttpEntity<Object>(headers);
        ResponseEntity<UserAddress> response =
                testRestTemplate.exchange(getBaseUrl()+"/addresses?zipcode=1111", HttpMethod.POST, requestEntity, UserAddress.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
             
    }

    // foi o serras q mandou
    @Test
    void testPostAddress_thenReturnCreate(){
        HttpHeaders headers= new HttpHeaders();
        headers.set("Authorization", "Bearer "+this.token);
        HttpEntity requestEntity = new HttpEntity<Object>(headers);
        ResponseEntity<UserAddress> response =
                testRestTemplate.exchange(getBaseUrl()+"/addresses?zipcode=1211-222&country=Portugal&city=Porto&address=Rua das Estias", HttpMethod.POST, requestEntity, UserAddress.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));
        assertThat(response.getBody().getUser().getName(), equalTo(this.user.getName()));

        ResponseEntity<List> responseGet =
                testRestTemplate.exchange(getBaseUrl()+"/addresses", HttpMethod.GET, requestEntity, List.class);
        assertThat(responseGet.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(responseGet.getBody().size(),equalTo(1));

        ResponseEntity<Address> response2 = testRestTemplate.exchange(getBaseUrl() + "/addresses/{id}", HttpMethod.GET,
                requestEntity, Address.class, 1);
        assertThat(response2.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(response2.getBody().getAddress(), equalTo("Rua das Estias"));
    }

    @Test
    void testGetAddressByInvalidId_thenReturnError(){
        HttpHeaders headers= new HttpHeaders();
        headers.set("Authorization", "Bearer "+this.token);
        HttpEntity requestEntity = new HttpEntity<Object>(headers);
        ResponseEntity<Address> response =
                testRestTemplate.exchange(getBaseUrl()+"/addresses/{id}", HttpMethod.GET, requestEntity, Address.class,-1);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));
    }

    

    private String getBaseUrl() {
        return "http://localhost:"+randomServerPort;
    }
}
