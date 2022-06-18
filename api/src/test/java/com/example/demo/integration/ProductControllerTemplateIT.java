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
import org.springframework.data.domain.Page;

import com.example.demo.models.Address;
import com.example.demo.models.Category;
import com.example.demo.models.OrderList;
import com.example.demo.models.Product;
import com.example.demo.models.ProductList;
import com.example.demo.models.ProductListItem;
import com.example.demo.models.Store;
import com.example.demo.models.User;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.CartListRepository;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.OrderListRepository;
import com.example.demo.repository.ProductListItemRepository;
import com.example.demo.repository.ProductListRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.StoreRepository;
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
public class ProductControllerTemplateIT {
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
    private ProductRepository prodRepository;

    @Autowired
    private CategoryRepository catRepository;

    
    @Autowired
    private AuthTokenFilter authTokenFilter;

    User user;
    String token;

    Product product;
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

        this.categoria = catRepository.saveAndFlush(categoria);
        catRepository.saveAndFlush(categoria1);
        catRepository.saveAndFlush(categoria2);
        Product p1 = new Product("tomate", 5.0f, "frescos", true, this.categoria);
        Product p2 = new Product("cebola",1.5f,"colhidas hoje",true,this.categoria);

        this.product=prodRepository.saveAndFlush(p1);
        prodRepository.saveAndFlush(p2);
    }
    @AfterEach
    public void resetDb() {
        prodRepository.deleteAll();
        prodRepository.flush();
        catRepository.deleteAll();
        catRepository.flush();
        userRepository.deleteAll();
        userRepository.flush();
    }
    @Test
    void testInvalidAddress_thenReturnNotFound() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + this.token);
        HttpEntity requestEntity = new HttpEntity<Object>(headers);
        ResponseEntity<Page> response = testRestTemplate.exchange(getBaseUrl() + "/products", HttpMethod.GET, requestEntity,
        Page.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(response.getBody().getSize(), equalTo(2));

    }

    @Test
    void whenPostProduct_thenCreateProduct() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + this.token);
        HttpEntity requestEntity = new HttpEntity<Object>(headers);
        ResponseEntity<Product> response = testRestTemplate.exchange(getBaseUrl() + "/products?name=tomate&description=frescos&price=5.0&category="+this.categoria.getId(), HttpMethod.POST, requestEntity,
        Product.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));

    }
    @Test
    void whenPostInvalidProduct_thenReturnError() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + this.token);
        HttpEntity requestEntity = new HttpEntity<Object>(headers);
        ResponseEntity<Product> response = testRestTemplate.exchange(getBaseUrl() + "/products?name=tomate&description=frescos&price=5.0&category=-1", HttpMethod.POST, requestEntity,
        Product.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));

    }

    @Test
    void whengetProduct_thenCreateProduct() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + this.token);
        HttpEntity requestEntity = new HttpEntity<Object>(headers);
        ResponseEntity<Product> response = testRestTemplate.exchange(getBaseUrl() + "/products/{id}", HttpMethod.GET, requestEntity,
        Product.class,this.product.getId());

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));

    }

    @Test
    void whengetInvalidIDProduct_thenCreateProduct() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + this.token);
        HttpEntity requestEntity = new HttpEntity<Object>(headers);
        ResponseEntity<Product> response = testRestTemplate.exchange(getBaseUrl() + "/products/{id}", HttpMethod.GET, requestEntity,
        Product.class,-1);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));

    }

    @Test
    void whengetInvalidIDProduct_thenDeleteFails() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + this.token);
        HttpEntity requestEntity = new HttpEntity<Object>(headers);
        ResponseEntity<Product> response = testRestTemplate.exchange(getBaseUrl() + "/products/{id}", HttpMethod.DELETE, requestEntity,
        Product.class,-1);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));

    }

    @Test
    void whenDeleteProduct_thenDeleteProduct() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + this.token);
        HttpEntity requestEntity = new HttpEntity<Object>(headers);
        ResponseEntity<Product> response = testRestTemplate.exchange(getBaseUrl() + "/products/{id}", HttpMethod.DELETE, requestEntity,
        Product.class,this.product.getId());

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));

    }

    @Test
    void whenUpdateProduct_thenUpdateProduct() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + this.token);
        HttpEntity requestEntity = new HttpEntity<Object>(headers);
        ResponseEntity<Product> response = testRestTemplate.exchange(getBaseUrl() + "/products/{id}?name=aaa", HttpMethod.PUT, requestEntity,
        Product.class,-1);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));

    }

    @Test
    void whenUpdateInvalidProduct_thenNotFound() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + this.token);
        HttpEntity requestEntity = new HttpEntity<Object>(headers);
        ResponseEntity<Product> response = testRestTemplate.exchange(getBaseUrl() + "/products/{id}?name=aaa", HttpMethod.PUT, requestEntity,
        Product.class,this.product.getId());

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));

    }

    private String getBaseUrl() {
        return "http://localhost:" + randomServerPort;
    }
}
