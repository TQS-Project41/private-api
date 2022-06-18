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
public class OrderControllerTemplateIT {
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
    private StoreRepository storeRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private OrderListRepository orderListRepository;
    @Autowired
    private AuthTokenFilter authTokenFilter;
    User user;
    String token;

    Category categoria;
    Address address;
    Store store;
    OrderList order;
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
        Address address = new Address("Portugal", "1201-222", "Aveiro", "Rua das Estia");
        Address address2 = new Address("Portugal", "1201-222", "Aveiro", "Rua das Estia");
        
        this.address= addressRepository.save(address);
        addressRepository.save(address2);

        Store store = new Store("Puma", address);
        this.store=storeRepository.save(store);
       /* 
        OrderList ret = new OrderList(new ProductList(user), address2, store, 1l);
        OrderList ret1 = new OrderList(new ProductList(user), address2, store, 2l);
        OrderList ret2 = new OrderList(new ProductList(user), address2, store, 3l);
       this.order= orderListRepository.save(ret);
        orderListRepository.save(ret1);
        orderListRepository.save(ret2);
        */
    }

    @AfterEach
    public void resetDb() {
        addressRepository.deleteAll();
        addressRepository.flush();
        storeRepository.deleteAll();
        storeRepository.flush();


        orderListRepository.deleteAll();
        orderListRepository.flush();

        userRepository.deleteAll();
        userRepository.flush();

    }


  /* 
    @Test
    void testInvalidAddress_thenReturnNotFound() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + this.token);
        HttpEntity requestEntity = new HttpEntity<Object>(headers);
        ResponseEntity<OrderList> response = testRestTemplate.exchange(getBaseUrl() + "/orders?store=555&address=555&deliveryTimestamp=111111", HttpMethod.POST, requestEntity,
        OrderList.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));
    }
    
    @Test
    void testInvalidStore_thenReturnNotFound() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + this.token);
        HttpEntity requestEntity = new HttpEntity<Object>(headers);
        ResponseEntity<List> response = testRestTemplate.exchange(getBaseUrl() + "/orders?store=555&address="+this.address.getId()+"&deliveryTimestamp=111111", HttpMethod.POST, requestEntity,
                List.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));
    }
    @Test
    void testPost_thenReturnOrder() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + this.token);
        HttpEntity requestEntity = new HttpEntity<Object>(headers);
        ResponseEntity<OrderList> response = testRestTemplate.exchange(getBaseUrl() + "/orders?store="+this.store.getId()
        +"&address="+this.address.getId()+"&deliveryTimestamp=111111", HttpMethod.POST, requestEntity,
        OrderList.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));
    }

    @Test
    void testGetOrders_thenReturnOrder() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + this.token);
        HttpEntity requestEntity = new HttpEntity<Object>(headers);
        ResponseEntity<OrderList> response = testRestTemplate.exchange(getBaseUrl() + "/orders/{id}", HttpMethod.GET, requestEntity,
        OrderList.class,-1);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));
    }

    @Test
    void testGetOrdersValid_thenReturnOrder() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + this.token);
        HttpEntity requestEntity = new HttpEntity<Object>(headers);
        ResponseEntity<OrderList> response = testRestTemplate.exchange(getBaseUrl() + "/orders/{id}", HttpMethod.GET, requestEntity,
        OrderList.class,this.order.getId());

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }


    @Test
    void testGetOrdersiValidUser_thenReturnForbidden() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + "");
        HttpEntity requestEntity = new HttpEntity<Object>(headers);
        ResponseEntity<OrderList> response = testRestTemplate.exchange(getBaseUrl() + "/orders/{id}", HttpMethod.POST, requestEntity,
        OrderList.class,this.order.getId());

        assertThat(response.getStatusCode(), equalTo(HttpStatus.FORBIDDEN));
    }
    @Test
    void testInvalidPostOrder_thenReturnBadArgs() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + this.token);
        HttpEntity requestEntity = new HttpEntity<Object>(headers);
        ResponseEntity<OrderList> response = testRestTemplate.exchange(getBaseUrl() + "/orders/", HttpMethod.POST, requestEntity,
        OrderList.class,this.order.getId());

        assertThat(response.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
    }

    @Test
    void testGetCartbyInvalidUser_thenReturnNotFound() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + this.token);
        HttpEntity requestEntity = new HttpEntity<Object>(headers);
        ResponseEntity<List> response = testRestTemplate.exchange(getBaseUrl() + "/orders/", HttpMethod.GET, requestEntity,
                List.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(response.getBody().size(), equalTo(3));


    }

    */
    private String getBaseUrl() {
        return "http://localhost:" + randomServerPort;
    }
}
