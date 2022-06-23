package com.example.demo.controller;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.models.Address;
import com.example.demo.models.OrderList;
import com.example.demo.models.ProductList;
import com.example.demo.models.Store;
import com.example.demo.models.User;
import com.example.demo.security.JwtUtils;
import com.example.demo.security.WebSecurityConfig;
import com.example.demo.service.AddressService;
import com.example.demo.service.DeliveryService;
import com.example.demo.service.OrderListService;
import com.example.demo.service.ProductService;
import com.example.demo.service.StoreService;
import com.example.demo.service.UserService;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

@WebMvcTest(value = OrderController.class, excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = WebSecurityConfig.class) })
@AutoConfigureMockMvc(addFilters = false)
public class OrderControllerMockMvcTest {
    @Autowired
  private MockMvc mvc;

  @MockBean
  private UserService userService;

  @MockBean
  private OrderListService orderService;

  @MockBean
  private JwtUtils jwtUtils;

  @MockBean
    private StoreService storeService;

    @MockBean
    private AddressService addressService;
    
  @MockBean
  private ProductService productService;

  @MockBean
  private DeliveryService deliveryService;
    
  @BeforeEach
  void setUp() {
    RestAssuredMockMvc.mockMvc(mvc);
  }

  @Test
    void whenGetOrders_thenGetOrders( ) throws Exception {
        User user= new User("alex20002011@gmail.com", "Alexandre", "pass",LocalDate.of(2000, 06, 28), "910123433", false, false);
        when(userService.getAuthenticatedUser()).thenReturn(Optional.of(user));
        Address address = new Address("Portugal", "1201-222", "Aveiro", "Rua das Estia");
        Address address2 = new Address("Portugal", "1201-222", "Aveiro", "Rua das Estia");
    
        Store store = new Store("Puma", address);
        OrderList ret = new OrderList(new ProductList(user), address2, store, 1l);
        OrderList ret1 = new OrderList(new ProductList(user), address2, store, 2l);
        OrderList ret2 = new OrderList(new ProductList(user), address2, store, 3l);
        Pageable pageable = Pageable.unpaged();

        Page<OrderList> res= new PageImpl<>(Arrays.asList(ret, ret1,ret2), pageable, 3);
        when(orderService.findAll(user, Pageable.unpaged())).thenReturn(res);
        RestAssuredMockMvc.given().
        contentType("application/json")
        .when().get("/orders").then().assertThat()
        .statusCode(200).and().
        body("content.deliveryId", hasItems(1,2,3));

    }
    @Test
    void testInvalidUser_thenReturnNotFound(){
      User user= new User("alex20002011@gmail.com", "Alexandre", "pass",LocalDate.of(2000, 06, 28), "910123433", false, false);
      when(userService.getAuthenticatedUser()).thenReturn(Optional.of(user));
        RestAssuredMockMvc.given()
                .contentType("application/json")
                .when()
                .post("/orders?store=1&address=1&deliveryTimestamp=111111")
                .then()
                .statusCode(404);
    }
    @Test
    void testInvalidAddress_thenReturnNotFound(){
      User user= new User("alex20002011@gmail.com", "Alexandre", "pass",LocalDate.of(2000, 06, 28), "910123433", false, false);
      Address address = new Address("Portugal", "1201-222", "Aveiro", "Rua das Estia");

      when(userService.getAuthenticatedUser()).thenReturn(Optional.of(user));
      when(addressService.getById(1)).thenReturn(null);
        RestAssuredMockMvc.given()
                .contentType("application/json")
                .when()
                .post("/orders?store=1&address=1&deliveryTimestamp=111111")
                .then()
                .statusCode(404);
    }

    @Test
    void testInvalidStore_thenReturnNotFound(){
      User user= new User("alex20002011@gmail.com", "Alexandre", "pass",LocalDate.of(2000, 06, 28), "910123433", false, false);
      Address address = new Address("Portugal", "1201-222", "Aveiro", "Rua das Estia");
      Store store = new Store("Puma", address);
      when(userService.getAuthenticatedUser()).thenReturn(Optional.of(user));
      when(addressService.getById(1)).thenReturn(address);
      when(storeService.getById(1)).thenReturn(null);

        RestAssuredMockMvc.given()
                .contentType("application/json")
                .when()
                .post("/orders?store=1&address=1&deliveryTimestamp=111111")
                .then()
                .statusCode(404);
    }

    @Test
    void testPost_thenReturnOrder() throws IOException, InterruptedException, ParseException{
      User user= new User("alex20002011@gmail.com", "Alexandre", "pass",LocalDate.of(2000, 06, 28), "910123433", false, false);
      Address address = new Address("Portugal", "1201-222", "Aveiro", "Rua das Estia");
      Store store = new Store("Puma", address);
      when(userService.getAuthenticatedUser()).thenReturn(Optional.of(user));
      when(addressService.getById(1)).thenReturn(address);
      when(storeService.getById(1)).thenReturn(store);
      when(deliveryService.postOrder(any())).thenReturn(1L);
        RestAssuredMockMvc.given()
                .contentType("application/json")
                .when()
                .post("/orders?store=1&address=1&deliveryTimestamp=10-05-2022 10:12")
                .then()
                .statusCode(201);
               
    }

    @Test
    void testGetOrders_thenReturnOrder(){
      User user= new User("alex20002011@gmail.com", "Alexandre", "pass",LocalDate.of(2000, 06, 28), "910123433", false, false);
      when(userService.getAuthenticatedUser()).thenReturn(Optional.of(user));
      when(orderService.findById(1l)).thenReturn(Optional.empty());
        RestAssuredMockMvc.given()
                .contentType("application/json")
                .when()
                .get("/orders/{id}",1)
                .then()
                .statusCode(404);
               
    }
    @Test
    void testDeleteOrders_thenReturnOrder(){
      User user= new User("alex20002011@gmail.com", "Alexandre", "pass",LocalDate.of(2000, 06, 28), "910123433", false, false);
      when(userService.getAuthenticatedUser()).thenReturn(Optional.of(user));
      when(orderService.findById(1l)).thenReturn(Optional.empty());
        RestAssuredMockMvc.given()
                .contentType("application/json")
                .when()
                .delete("/orders/{id}",1)
                .then()
                .statusCode(404);
               
    }
    @Test
    void testGetOrdersInvalidUser_thenReturnNotFOund(){
      User user= new User("alex20002011@gmail.com", "Alexandre", "pass",LocalDate.of(2000, 06, 28), "910123433", false, false);
      Address address = new Address("Portugal", "1201-222", "Aveiro", "Rua das Estia");
      Store store = new Store("Puma", address);
      when(userService.getAuthenticatedUser()).thenReturn(Optional.empty());
      OrderList ret = new OrderList(new ProductList(user), address, store, 1l);
      when(orderService.findById(1l)).thenReturn(Optional.of(ret));
        RestAssuredMockMvc.given()
                .contentType("application/json")
                .when()
                .get("/orders/{id}",1)
                .then()
                .statusCode(404);
               
    }

    @Test
    void testDeleteOrdersInvalidUser_thenReturnNotFOund(){
      User user= new User("alex20002011@gmail.com", "Alexandre", "pass",LocalDate.of(2000, 06, 28), "910123433", false, false);
      Address address = new Address("Portugal", "1201-222", "Aveiro", "Rua das Estia");
      Store store = new Store("Puma", address);
      when(userService.getAuthenticatedUser()).thenReturn(Optional.empty());
      OrderList ret = new OrderList(new ProductList(user), address, store, 1l);
      when(orderService.findById(1l)).thenReturn(Optional.of(ret));
        RestAssuredMockMvc.given()
                .contentType("application/json")
                .when()
                .delete("/orders/{id}",1)
                .then()
                .statusCode(404);
               
    }


    @Test
    void testGetOrdersValid_thenReturnOrder() throws IOException, InterruptedException, ParseException{
      User user= new User("alex20002011@gmail.com", "Alexandre", "pass",LocalDate.of(2000, 06, 28), "910123433", false, false);
      Address address = new Address("Portugal", "1201-222", "Aveiro", "Rua das Estia");
      Store store = new Store("Puma", address);
      HashMap<String, Object> ret_map = new HashMap<>();
      ret_map.put("status", "QUEUED");
      when(userService.getAuthenticatedUser()).thenReturn(Optional.of(user));
      OrderList ret = new OrderList(new ProductList(user), address, store, 1l);
      when(orderService.findById(1l)).thenReturn(Optional.of(ret));
      when(deliveryService.getDelivery(anyLong())).thenReturn(ret_map);

        RestAssuredMockMvc.given()
                .contentType("application/json")
                .when()
                .get("/orders/{id}",1)
                .then()
                .statusCode(200).and().body("status", equalTo("QUEUED"));
               
    }

    @Test
    void testDeleteOrdersValid_thenReturnOrder() throws IOException, InterruptedException, ParseException{
      User user= new User("alex20002011@gmail.com", "Alexandre", "pass",LocalDate.of(2000, 06, 28), "910123433", false, false);
      Address address = new Address("Portugal", "1201-222", "Aveiro", "Rua das Estia");
      Store store = new Store("Puma", address);
      HashMap<String, Object> ret_map = new HashMap<>();
      ret_map.put("status", "CANCELED");
      when(userService.getAuthenticatedUser()).thenReturn(Optional.of(user));
      OrderList ret = new OrderList(new ProductList(user), address, store, 1l);
      when(orderService.findById(1l)).thenReturn(Optional.of(ret));
      when(deliveryService.cancelDelivery(anyLong())).thenReturn(ret_map);

        RestAssuredMockMvc.given()
                .contentType("application/json")
                .when()
                .delete("/orders/{id}",1)
                .then()
                .statusCode(200).and().body("status", equalTo("CANCELED"));
               
    }

    @Test
    void testGetOrdersiValidUser_thenReturnForbidden(){
      User user1= new User("alex20002ss011@gmail.com", "Alexandre", "pass",LocalDate.of(2000, 06, 28), "910123433", false, false);
      User user= new User("alex20002011@gmail.com", "Alexandre", "pass",LocalDate.of(2000, 06, 28), "910123433", false, false);
      user1.setId(1l);

      Address address = new Address("Portugal", "1201-222", "Aveiro", "Rua das Estia");
      Store store = new Store("Puma", address);
      when(userService.getAuthenticatedUser()).thenReturn(Optional.of(user1));
      OrderList ret = new OrderList(new ProductList(user), address, store, 1l);
      when(orderService.findById(1l)).thenReturn(Optional.of(ret));
        RestAssuredMockMvc.given()
                .contentType("application/json")
                .when()
                .get("/orders/{id}",1)
                .then()
                .statusCode(403);
               
    }


    @Test
    void testGetOrdersiValidUserAdmin_thenReturnOrder() throws IOException, InterruptedException, ParseException{
      User user1= new User("alex20002ss011@gmail.com", "Alexandre", "pass",LocalDate.of(2000, 06, 28), "910123433", true, false);
      User user= new User("alex20002011@gmail.com", "Alexandre", "pass",LocalDate.of(2000, 06, 28), "910123433", false, false);
      Address address = new Address("Portugal", "1201-222", "Aveiro", "Rua das Estia");
      Store store = new Store("Puma", address);
 
      HashMap<String, Object> ret_map = new HashMap<>();
      ret_map.put("status", "QUEUED");
      when(userService.getAuthenticatedUser()).thenReturn(Optional.of(user));
      OrderList ret = new OrderList(new ProductList(user), address, store, 1l);
      when(orderService.findById(1l)).thenReturn(Optional.of(ret));
      when(deliveryService.getDelivery(anyLong())).thenReturn(ret_map);
        RestAssuredMockMvc.given()
                .contentType("application/json")
                .when()
                .get("/orders/{id}",1)
                .then()
                .statusCode(200);
               
    }

    @Test
    void testGetOrdersiValidUserStaff_thenReturnOrder() throws IOException, InterruptedException, ParseException{
      User user1= new User("alex20002ss011@gmail.com", "Alexandre", "pass",LocalDate.of(2000, 06, 28), "910123433", false, true);
      User user= new User("alex20002011@gmail.com", "Alexandre", "pass",LocalDate.of(2000, 06, 28), "910123433", false, false);
      Address address = new Address("Portugal", "1201-222", "Aveiro", "Rua das Estia");
      Store store = new Store("Puma", address);
      HashMap<String, Object> ret_map = new HashMap<>();
      ret_map.put("status", "QUEUED");
      when(userService.getAuthenticatedUser()).thenReturn(Optional.of(user));
      OrderList ret = new OrderList(new ProductList(user), address, store, 1l);
      when(orderService.findById(1l)).thenReturn(Optional.of(ret));
      when(deliveryService.getDelivery(anyLong())).thenReturn(ret_map);
        RestAssuredMockMvc.given()
                .contentType("application/json")
                .when()
                .get("/orders/{id}",1)
                .then()
                .statusCode(200);
               
    }

    
    @Test
    void testGetOrdersByUser_thenReturnOrder(){
      User user= new User("alex20002011@gmail.com", "Alexandre", "pass",LocalDate.of(2000, 06, 28), "910123433", false, false);
      when(userService.getAuthenticatedUser()).thenReturn(Optional.empty());

        RestAssuredMockMvc.given()
                .contentType("application/json")
                .when()
                .get("/orders/{id}",1)
                .then()
                .statusCode(404);
               
    }

    @Test
    void testDeleteOrdersByUser_thenReturnOrder(){
      User user= new User("alex20002011@gmail.com", "Alexandre", "pass",LocalDate.of(2000, 06, 28), "910123433", false, false);
      when(userService.getAuthenticatedUser()).thenReturn(Optional.empty());

        RestAssuredMockMvc.given()
                .contentType("application/json")
                .when()
                .delete("/orders/{id}",1)
                .then()
                .statusCode(404);
               
    }

    @Test
    void testInvalidPostOrder_thenReturnBadArgs(){
        when(userService.getAuthenticatedUser()).thenReturn(Optional. empty() );
        RestAssuredMockMvc.given()
                .contentType("application/json")
                .when()
                .post("/orders/")
                .then()
                .statusCode(400);
    }

  @Test
  void testGetCartbyInvalidUser_thenReturnNotFound(){
      when(userService.getAuthenticatedUser()).thenReturn(Optional. empty() );
      RestAssuredMockMvc.given()
              .contentType("application/json")
              .when()
              .get("/orders/")
              .then()
              .statusCode(200).and().
              body("content.deliveryId", hasItems());
            
  }

  @Test
  void testGetFee_thenReturnFee() throws IOException, InterruptedException, ParseException{
    HashMap<String, Double> map = new HashMap<>();
    map.put("fee", 5.0);
      when(deliveryService.getFee(anyLong())).thenReturn(map);

      RestAssuredMockMvc.given()
              .contentType("application/json")
              .when()
              .get("/orders/{id}/fee",1)
              .then()
              .statusCode(200).and().
              body("fee", equalTo(5.0F));
            
  }
    
    
}
