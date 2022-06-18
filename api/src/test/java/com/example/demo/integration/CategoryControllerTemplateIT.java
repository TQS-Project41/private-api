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
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.example.demo.models.Category;
import com.example.demo.models.Product;
import com.example.demo.models.ProductListItem;
import com.example.demo.models.User;
import com.example.demo.repository.CartListRepository;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductListItemRepository;
import com.example.demo.repository.ProductListRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.AuthTokenFilter;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class CategoryControllerTemplateIT {
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
    private CategoryRepository categoryRepository;

    User user;
    String token;

    Category categoria;
    @BeforeEach
    public void setUp() {

        // LOGIN
        User user = new User("alex20002011@gmail.com", "Alexandre", "pass", LocalDate.of(2000, 06, 28), "910123433",
                false, false);

        this.user = userRepository.saveAndFlush(user);
        Map<String, String> request = new HashMap<>();
        request.put("email", "alex20002011@gmail.com");
        request.put("password", "pass");
        ResponseEntity<Map> response = testRestTemplate.postForEntity("http://localhost:" + randomServerPort + "/login",
                request, Map.class);
        this.token = response.getBody().get("token").toString();
        // FINAL LOGIN
        Category categoria = new Category("FRUTA", true);
        Category categoria1 = new Category("LEGUMES", true);
        Category categoria2 = new Category("VEGETAIS", true);

        this.categoria = categoryRepository.saveAndFlush(categoria);
        categoryRepository.saveAndFlush(categoria1);
        categoryRepository.saveAndFlush(categoria2);

    }

    @AfterEach
    public void resetDb() {
        userRepository.deleteAll();
        categoryRepository.deleteAll();

        userRepository.flush();
        categoryRepository.flush();

    }

    @Test
    void testGetAddressbyInvalidUser_thenReturnNotFound() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + "");
        HttpEntity requestEntity = new HttpEntity<Object>(headers);
        ResponseEntity<List> response = testRestTemplate.exchange(getBaseUrl() + "/categories", HttpMethod.GET, requestEntity,
                List.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.FORBIDDEN));

    }
    @Test
    void testGetAddressbyInvalidUser_thenReturnAllCategories() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + this.token);
        HttpEntity requestEntity = new HttpEntity<Object>(headers);
        ResponseEntity<List> response = testRestTemplate.exchange(getBaseUrl() + "/categories", HttpMethod.GET, requestEntity,
                List.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(response.getBody().size(), equalTo(3));


    }
    @Test
    void whenPostCategory_thenCreateCategory() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + this.token);
        HttpEntity requestEntity = new HttpEntity<Object>(headers);
        ResponseEntity<Category> response = testRestTemplate.exchange(getBaseUrl() + "/categories?name=PEIXE", HttpMethod.POST, requestEntity,
        Category.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));
    }

    @Test
    void whenPostInvalidCategory_thenFailCreate() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + this.token);
        HttpEntity requestEntity = new HttpEntity<Object>(headers);
        ResponseEntity<Category> response = testRestTemplate.exchange(getBaseUrl() + "/categories?name=legumes", HttpMethod.POST, requestEntity,
        Category.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));


    }

    @Test
    void whenPostInvalidCategory_thenFailUpdate() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + this.token);
        HttpEntity requestEntity = new HttpEntity<Object>(headers);
        ResponseEntity<Category> response = testRestTemplate.exchange(getBaseUrl() + "/categories/{id}?name=aaaa", HttpMethod.PUT, requestEntity,
        Category.class,5);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));


    }

    @Test
    void whenPostInvalidCategory_thenFailUpdatev2() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + this.token);
        HttpEntity requestEntity = new HttpEntity<Object>(headers);
        ResponseEntity<Category> response = testRestTemplate.exchange(getBaseUrl() + "/categories/{id}?name=LEGUMES", HttpMethod.PUT, requestEntity,
        Category.class,categoria.getId());
        assertThat(response.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));


    }
    @Test
    void whenPostInvalidCategory_thenUpdates() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + this.token);
        HttpEntity requestEntity = new HttpEntity<Object>(headers);
        ResponseEntity<Category> response = testRestTemplate.exchange(getBaseUrl() + "/categories/{id}?name=cebola", HttpMethod.PUT, requestEntity,
        Category.class,categoria.getId());
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));


    }

    @Test
    void whenINvalidCategory_then404Return() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + this.token);
        HttpEntity requestEntity = new HttpEntity<Object>(headers);
        ResponseEntity<Category> response = testRestTemplate.exchange(getBaseUrl() + "/categories/{id}", HttpMethod.DELETE, requestEntity,
        Category.class,5);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));
    }

    @Test
    void whenDeleteCategory_thenDeleted() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + this.token);
        HttpEntity requestEntity = new HttpEntity<Object>(headers);
        ResponseEntity<Category> response = testRestTemplate.exchange(getBaseUrl() + "/categories/{id}", HttpMethod.DELETE, requestEntity,
        Category.class,1);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));
    }

    private String getBaseUrl() {
        return "http://localhost:" + randomServerPort;
    }
}
