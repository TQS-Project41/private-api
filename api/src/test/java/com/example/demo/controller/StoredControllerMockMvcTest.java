package com.example.demo.controller;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
import com.example.demo.models.SavedList;
import com.example.demo.models.Product;
import com.example.demo.models.ProductListItem;

import com.example.demo.models.Category;


import com.example.demo.models.Store;
import com.example.demo.models.User;
import com.example.demo.security.JwtUtils;
import com.example.demo.security.WebSecurityConfig;
import com.example.demo.service.AddressService;
import com.example.demo.service.OrderListService;
import com.example.demo.service.ProductService;
import com.example.demo.service.CategoryService;

import com.example.demo.service.StoreService;
import com.example.demo.service.UserService;
import com.example.demo.service.SavedListService;


import io.restassured.module.mockmvc.RestAssuredMockMvc;

@WebMvcTest(value = StoredController.class, excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = WebSecurityConfig.class) })
@AutoConfigureMockMvc(addFilters = false)
public class StoredControllerMockMvcTest {
    @Autowired
    private MockMvc mvc;
  
    @MockBean
    private UserService userService;
  
    @MockBean
    private OrderListService orderService;

    @MockBean
    private SavedListService savedService;
  
    @MockBean
    private ProductService productService;

    @MockBean
    private CategoryService categoryService;
    @MockBean
    private JwtUtils jwtUtils;
  
    @MockBean
      private StoreService storeService;
  
      @MockBean
      private AddressService addressService;
      
     
    
  
    @BeforeEach
    void setUp() {
      RestAssuredMockMvc.mockMvc(mvc);
    }

    @Test
    void whenNOuser_thenGetBlankPage( ) throws Exception {
        User user= new User("alex20002011@gmail.com", "Alexandre", "pass",LocalDate.of(2000, 06, 28), "910123433", false, false);
        when(userService.getAuthenticatedUser()).thenReturn(Optional.empty());
    
        SavedList ret = new SavedList(new ProductList(user), "aaaa");
        SavedList ret1 = new SavedList(new ProductList(user), "bbb");
        SavedList ret2 = new SavedList(new ProductList(user), "ccc");

        Pageable pageable = Pageable.unpaged();

        Page<SavedList> res= new PageImpl<>(Arrays.asList(ret, ret1,ret2), pageable, 3);
        when(savedService.findAll(user, Pageable.unpaged())).thenReturn(res);
        RestAssuredMockMvc.given().
        contentType("application/json")
        .when().get("/stored_lists").then().assertThat().statusCode(200).and().
        body("size", equalTo(0));
    }

    @Test
    void whenGetsaved_thenGetsaved( ) throws Exception {
        User user= new User("alex20002011@gmail.com", "Alexandre", "pass",LocalDate.of(2000, 06, 28), "910123433", false, false);
        when(userService.getAuthenticatedUser()).thenReturn(Optional.of(user));
    
        SavedList ret = new SavedList(new ProductList(user), "aaaa");
        SavedList ret1 = new SavedList(new ProductList(user), "bbb");
        SavedList ret2 = new SavedList(new ProductList(user), "ccc");

        Pageable pageable = Pageable.unpaged();

        Page<SavedList> res= new PageImpl<>(Arrays.asList(ret, ret1,ret2), pageable, 3);
        when(savedService.findAll(user, Pageable.unpaged())).thenReturn(res);
        RestAssuredMockMvc.given().
        contentType("application/json")
        .when().get("/stored_lists").then().assertThat()
        .statusCode(200).and().
        body("content.name", hasItems("aaaa","bbb","ccc"));

    }

    @Test
    void whenPostsaved_thenPostsaved( ) throws Exception {
        User user= new User("alex20002011@gmail.com", "Alexandre", "pass",LocalDate.of(2000, 06, 28), "910123433", false, false);
        when(userService.getAuthenticatedUser()).thenReturn(Optional.of(user));
    
        SavedList ret = new SavedList(new ProductList(user), "aaaa");


        when(savedService.save(any())).thenReturn(ret);
        RestAssuredMockMvc.given().
        contentType("application/json")
        .when().post("/stored_lists?name=aaaa").then().assertThat()
        .statusCode(200).and().
        body("name", equalTo("aaaa"));

    }
    @Test
    void whenPNoUserpostsaved_thenNoUser( ) throws Exception {
        User user= new User("alex20002011@gmail.com", "Alexandre", "pass",LocalDate.of(2000, 06, 28), "910123433", false, false);
        when(userService.getAuthenticatedUser()).thenReturn(Optional.empty());
    
        SavedList ret = new SavedList(new ProductList(user), "aaaa");


        when(savedService.save(any())).thenReturn(ret);
        RestAssuredMockMvc.given().
        contentType("application/json")
        .when().post("/stored_lists?name=aaaa").then().assertThat()
        .statusCode(404);

    }

    @Test
    void testGetSaved_thenReturnNOList(){
      User user= new User("alex20002011@gmail.com", "Alexandre", "pass",LocalDate.of(2000, 06, 28), "910123433", false, false);
      when(userService.getAuthenticatedUser()).thenReturn(Optional.of(user));
      when(savedService.findById(1l)).thenReturn(Optional.empty());
        RestAssuredMockMvc.given()
                .contentType("application/json")
                .when()
                .get("/stored_lists/{id}",1)
                .then()
                .statusCode(404);
               
    }
    @Test
    void testGetSaved_thenReturnSaved(){
      User user= new User("alex20002011@gmail.com", "Alexandre", "pass",LocalDate.of(2000, 06, 28), "910123433", false, false);
      SavedList ret = new SavedList(new ProductList(user), "aaaa");
     
      when(userService.getAuthenticatedUser()).thenReturn(Optional.empty());
      when(savedService.findById(1l)).thenReturn(Optional.of(ret));
        RestAssuredMockMvc.given()
                .contentType("application/json")
                .when()
                .get("/stored_lists/{id}",1)
                .then()
                .statusCode(404);
               
    }

    @Test
    void testGetSaved_thenReturnSavedList(){
      User user= new User("alex20002011@gmail.com", "Alexandre", "pass",LocalDate.of(2000, 06, 28), "910123433", false, false);
      SavedList ret = new SavedList(new ProductList(user), "aaaa");
     
      when(userService.getAuthenticatedUser()).thenReturn(Optional.of(user));
      when(savedService.findById(1l)).thenReturn(Optional.of(ret));

        RestAssuredMockMvc.given()
                .contentType("application/json")
                .when()
                .get("/stored_lists/{id}",1)
                .then()
                .statusCode(200);
               
    }
    @Test
    void testGetInvalidUserSaved_thenReturnForbidden(){
      User user= new User("alex20002011@gmail.com", "Alexandre", "pass",LocalDate.of(2000, 06, 28), "910123433", false, false);
    User user1= new User("alex20002ss011@gmail.com", "Alexandre", "pass",LocalDate.of(2000, 06, 28), "910123433", false, false);
    SavedList ret = new SavedList(new ProductList(user1), "aaaa");
        
      when(userService.getAuthenticatedUser()).thenReturn(Optional.of(user));
      when(savedService.findById(1l)).thenReturn(Optional.of(ret));

        RestAssuredMockMvc.given()
                .contentType("application/json")
                .when()
                .get("/stored_lists/{id}",1)
                .then()
                .statusCode(403);
               
    }
    @Test
    void testGetUserStaff_thenReturnSaved(){
      User user= new User("alex20002011@gmail.com", "Alexandre", "pass",LocalDate.of(2000, 06, 28), "910123433", false, false);
    User user1= new User("alex20002ss011@gmail.com", "Alexandre", "pass",LocalDate.of(2000, 06, 28), "910123433", true, false);
    SavedList ret = new SavedList(new ProductList(user1), "aaaa");
        
      when(userService.getAuthenticatedUser()).thenReturn(Optional.of(user));
      when(savedService.findById(1l)).thenReturn(Optional.of(ret));

        RestAssuredMockMvc.given()
                .contentType("application/json")
                .when()
                .get("/stored_lists/{id}",1)
                .then()
                .statusCode(403);
               
    }
    @Test
    void testGetUserSavedAdmin_thenReturnSAved(){
      User user= new User("alex20002011@gmail.com", "Alexandre", "pass",LocalDate.of(2000, 06, 28), "910123433", false, false);
    User user1= new User("alex20002ss011@gmail.com", "Alexandre", "pass",LocalDate.of(2000, 06, 28), "910123433", false, true);
    SavedList ret = new SavedList(new ProductList(user1), "aaaa");
        
      when(userService.getAuthenticatedUser()).thenReturn(Optional.of(user));
      when(savedService.findById(1l)).thenReturn(Optional.of(ret));

        RestAssuredMockMvc.given()
                .contentType("application/json")
                .when()
                .get("/stored_lists/{id}",1)
                .then()
                .statusCode(403);
               
    }

    @Test
    void testDeleteSaved_thenReturnNOList(){
      User user= new User("alex20002011@gmail.com", "Alexandre", "pass",LocalDate.of(2000, 06, 28), "910123433", false, false);
      when(userService.getAuthenticatedUser()).thenReturn(Optional.of(user));
      when(savedService.findById(1l)).thenReturn(Optional.empty());
        RestAssuredMockMvc.given()
                .contentType("application/json")
                .when()
                .delete("/stored_lists/{id}",1)
                .then()
                .statusCode(404);
               
    }
    @Test
    void testDeleteSaved_thenReturnSaved(){
      User user= new User("alex20002011@gmail.com", "Alexandre", "pass",LocalDate.of(2000, 06, 28), "910123433", false, false);
      SavedList ret = new SavedList(new ProductList(user), "aaaa");
     
      when(userService.getAuthenticatedUser()).thenReturn(Optional.empty());
      when(savedService.findById(1l)).thenReturn(Optional.of(ret));
        RestAssuredMockMvc.given()
                .contentType("application/json")
                .when()
                .delete("/stored_lists/{id}",1)
                .then()
                .statusCode(404);
               
    }

    @Test
    void testDeleteSaved_thenReturnSavedList(){
      User user= new User("alex20002011@gmail.com", "Alexandre", "pass",LocalDate.of(2000, 06, 28), "910123433", false, false);
      SavedList ret = new SavedList(new ProductList(user), "aaaa");
     
      when(userService.getAuthenticatedUser()).thenReturn(Optional.of(user));
      when(savedService.findById(1l)).thenReturn(Optional.of(ret));

        RestAssuredMockMvc.given()
                .contentType("application/json")
                .when()
                .delete("/stored_lists/{id}",1)
                .then()
                .statusCode(200);
               
    }
    @Test
    void testDeleteInvalidUserSaved_thenReturnForbidden(){
      User user= new User("alex20002011@gmail.com", "Alexandre", "pass",LocalDate.of(2000, 06, 28), "910123433", false, false);
    User user1= new User("alex20002ss011@gmail.com", "Alexandre", "pass",LocalDate.of(2000, 06, 28), "910123433", false, false);
    SavedList ret = new SavedList(new ProductList(user1), "aaaa");
        
      when(userService.getAuthenticatedUser()).thenReturn(Optional.of(user));
      when(savedService.findById(1l)).thenReturn(Optional.of(ret));

        RestAssuredMockMvc.given()
                .contentType("application/json")
                .when()
                .delete("/stored_lists/{id}",1)
                .then()
                .statusCode(403);
               
    }
    @Test
    void testDeleteUserStaff_thenReturnSaved(){
      User user= new User("alex20002011@gmail.com", "Alexandre", "pass",LocalDate.of(2000, 06, 28), "910123433", false, false);
    User user1= new User("alex20002ss011@gmail.com", "Alexandre", "pass",LocalDate.of(2000, 06, 28), "910123433", true, false);
    SavedList ret = new SavedList(new ProductList(user1), "aaaa");
        
      when(userService.getAuthenticatedUser()).thenReturn(Optional.of(user));
      when(savedService.findById(1l)).thenReturn(Optional.of(ret));

        RestAssuredMockMvc.given()
                .contentType("application/json")
                .when()
                .delete("/stored_lists/{id}",1)
                .then()
                .statusCode(403);
               
    }
    @Test
    void testDeleteUserSavedAdmin_thenReturnSAved(){
      User user= new User("alex20002011@gmail.com", "Alexandre", "pass",LocalDate.of(2000, 06, 28), "910123433", false, false);
    User user1= new User("alex20002ss011@gmail.com", "Alexandre", "pass",LocalDate.of(2000, 06, 28), "910123433", false, true);
    SavedList ret = new SavedList(new ProductList(user1), "aaaa");
        
      when(userService.getAuthenticatedUser()).thenReturn(Optional.of(user));
      when(savedService.findById(1l)).thenReturn(Optional.of(ret));

        RestAssuredMockMvc.given()
                .contentType("application/json")
                .when()
                .delete("/stored_lists/{id}",1)
                .then()
                .statusCode(403);
               
    }



    @Test
    void testPostSaved_thenReturnNOList(){
        Category a = new Category("Legumes", true);
        Product p1 = new Product("tomate", 5.0f, "frescos", true, a);
        
        User user= new User("alex20002011@gmail.com", "Alexandre", "pass",LocalDate.of(2000, 06, 28), "910123433", false, false);
      when(productService.getById(1L)).thenReturn(Optional.empty());
    
        when(userService.getAuthenticatedUser()).thenReturn(Optional.of(user));
      when(savedService.findById(1l)).thenReturn(Optional.empty());
        RestAssuredMockMvc.given()
                .contentType("application/json")
                .when()
                .post("/stored_lists/{id}?product=1&amount=1",1)
                .then()
                .statusCode(404);
               
    }
    @Test
    void testPostSaved_thenReturnSaved(){
        Category a = new Category("Legumes", true);
        Product p1 = new Product("tomate", 5.0f, "frescos", true, a);
        
        User user= new User("alex20002011@gmail.com", "Alexandre", "pass",LocalDate.of(2000, 06, 28), "910123433", false, false);
      when(productService.getById(1L)).thenReturn(Optional.of(p1));
      SavedList ret = new SavedList(new ProductList(user), "aaaa");
     
      when(userService.getAuthenticatedUser()).thenReturn(Optional.empty());
      when(savedService.findById(1l)).thenReturn(Optional.of(ret));
        RestAssuredMockMvc.given()
                .contentType("application/json")
                .when()
                .post("/stored_lists/{id}?product=1&amount=1",1)
                .then()
                .statusCode(404);
               
    }

    @Test
    void testPostSaved_thenReturnSavedList(){
        Category a = new Category("Legumes", true);
        Product p1 = new Product("tomate", 5.0f, "frescos", true, a);
        
        User user= new User("alex20002011@gmail.com", "Alexandre", "pass",LocalDate.of(2000, 06, 28), "910123433", false, false);
      SavedList ret = new SavedList(new ProductList(user), "aaaa");
      ProductListItem prod= new ProductListItem(1, new ProductList(user), p1);
      when(userService.getAuthenticatedUser()).thenReturn(Optional.of(user));
      when(savedService.updateListItem(ret,p1,1)).thenReturn(Optional.of(prod));
      when(savedService.findById(1l)).thenReturn(Optional.of(ret));
      when(productService.getById(1)).thenReturn(Optional.of(p1));
        RestAssuredMockMvc.given()
                .contentType("application/json")
                .when()
                .post("/stored_lists/{id}?product=1&amount=1",1)
                .then()
                .statusCode(200);
               
    }
    @Test
    void testPostInvalidUserSaved_thenReturnForbidden(){
    User user1= new User("alex20002ss011@gmail.com", "Alexandre", "pass",LocalDate.of(2000, 06, 28), "910123433", false, false);
    Category a = new Category("Legumes", true);
    Product p1 = new Product("tomate", 5.0f, "frescos", true, a);
    
    User user= new User("alex20002011@gmail.com", "Alexandre", "pass",LocalDate.of(2000, 06, 28), "910123433", false, false);
  SavedList ret = new SavedList(new ProductList(user1), "aaaa");
  ProductListItem prod= new ProductListItem(1, new ProductList(user1), p1);
  when(userService.getAuthenticatedUser()).thenReturn(Optional.of(user));
  when(savedService.updateListItem(ret,p1,1)).thenReturn(Optional.of(prod));
  when(savedService.findById(1l)).thenReturn(Optional.of(ret));
  when(productService.getById(1)).thenReturn(Optional.of(p1));

        RestAssuredMockMvc.given()
                .contentType("application/json")
                .when()
                .post("/stored_lists/{id}?product=1&amount=1",1)
                .then()
                .statusCode(403);
               
    }
    @Test
    void testPostUserStaff_thenReturnSaved(){
    User user1= new User("alex20002ss011@gmail.com", "Alexandre", "pass",LocalDate.of(2000, 06, 28), "910123433", true, false);
    Category a = new Category("Legumes", true);
    Product p1 = new Product("tomate", 5.0f, "frescos", true, a);
    
    User user= new User("alex20002011@gmail.com", "Alexandre", "pass",LocalDate.of(2000, 06, 28), "910123433", false, false);
  SavedList ret = new SavedList(new ProductList(user1), "aaaa");
  ProductListItem prod= new ProductListItem(1, new ProductList(user), p1);
  when(userService.getAuthenticatedUser()).thenReturn(Optional.of(user));
  when(savedService.updateListItem(ret,p1,1)).thenReturn(Optional.of(prod));
  when(savedService.findById(1l)).thenReturn(Optional.of(ret));
  when(productService.getById(1)).thenReturn(Optional.of(p1));

        RestAssuredMockMvc.given()
                .contentType("application/json")
                .when()
                .post("/stored_lists/{id}?product=1&amount=1",1)
                .then()
                .statusCode(403);
               
    }
    @Test
    void testPostUserSavedAdmin_thenReturnSAved(){
    User user1= new User("alex20002ss011@gmail.com", "Alexandre", "pass",LocalDate.of(2000, 06, 28), "910123433", false, true);
    Category a = new Category("Legumes", true);
    Product p1 = new Product("tomate", 5.0f, "frescos", true, a);
    
    User user= new User("alex20002011@gmail.com", "Alexandre", "pass",LocalDate.of(2000, 06, 28), "910123433", false, false);
  SavedList ret = new SavedList(new ProductList(user1), "aaaa");
  ProductListItem prod= new ProductListItem(1, new ProductList(user), p1);
  when(userService.getAuthenticatedUser()).thenReturn(Optional.of(user));
  when(savedService.updateListItem(ret,p1,1)).thenReturn(Optional.of(prod));
  when(savedService.findById(1l)).thenReturn(Optional.of(ret));
  when(productService.getById(1)).thenReturn(Optional.of(p1));

        RestAssuredMockMvc.given()
                .contentType("application/json")
                .when()
                .post("/stored_lists/{id}?product=1&amount=1",1)
                .then()
                .statusCode(403);
               
    }
}
