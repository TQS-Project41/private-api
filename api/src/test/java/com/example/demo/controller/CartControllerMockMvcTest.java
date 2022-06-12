package com.example.demo.controller;

import static org.mockito.Mockito.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.demo.models.Address;
import com.example.demo.models.Category;
import com.example.demo.models.Product;
import com.example.demo.models.ProductList;
import com.example.demo.models.ProductListItem;
import com.example.demo.models.User;
import com.example.demo.models.UserAddress;
import com.example.demo.security.AuthTokenFilter;
import com.example.demo.security.JwtUtils;
import com.example.demo.security.WebSecurityConfig;
import com.example.demo.service.AddressService;
import com.example.demo.service.CartListService;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ProductService;
import com.example.demo.service.UserService;

import static org.hamcrest.Matchers.*;
import org.springframework.context.annotation.FilterType;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

@WebMvcTest(value = CartController.class, excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = WebSecurityConfig.class)})
@AutoConfigureMockMvc(addFilters = false)
public class CartControllerMockMvcTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private CartListService cartService;

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private ProductService prodService;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtUtils jwtUtils;

    @MockBean
    private AuthTokenFilter authTokenFilter;

    @MockBean
    private SecurityContextHolder securityContextHolder;

    @BeforeEach
    void setUp()  {
        RestAssuredMockMvc.mockMvc(mvc);
    }

    @Test
    void testGetCartbyInvalidUser_thenReturnNotFound(){
        when(userService.getAuthenticatedUser()).thenReturn(Optional. empty() );
        RestAssuredMockMvc.given()
                .contentType("application/json")
                .when()
                .get("/addresses/")
                .then()
                .statusCode(404);
             
    }


    @Test
    void testGetcartbyInvalidUser_thenReturnCart(){
        User user= new User("alex20002011@gmail.com", "Alexandre", "pass",LocalDate.of(2000, 06, 28), "910123433", false, false);
        when(userService.getAuthenticatedUser()).thenReturn(Optional.of(user));
        List<ProductListItem> ret = new ArrayList<>();
        Category categoria = new Category("FRUTA", true);
        Product x2 = new Product("pera", 2f, "doces", true, categoria);
        Product x = new Product("maça", 1.5f, "doces", true, categoria);
        ProductList list = new ProductList(user);
        ProductListItem p1 = new ProductListItem(1, list, x);
        ProductListItem p2 = new ProductListItem(1, list, x2);
        ret.add(p1);
        ret.add(p2);
        when(cartService.getCurrentCartItems(user)).thenReturn(ret);
        RestAssuredMockMvc.given()
                .contentType("application/json")
                .when()
                .get("/cart/")
                .then()
                .statusCode(200).and().
                body("[0].product.name", equalTo("maça")).
                body("[1].product.name", equalTo("pera"));
        verify(userService, times(1)).getAuthenticatedUser();
        verify(cartService, times(1)).getCurrentCartItems(user);             
    }


    @Test
    void testPostItemInCart_thenReturnNewItem(){
        User user= new User("alex20002011@gmail.com", "Alexandre", "pass",LocalDate.of(2000, 06, 28), "910123433", false, false);
        when(userService.getAuthenticatedUser()).thenReturn(Optional.of(user));
        Category categoria = new Category("FRUTA", true);
        Product x2 = new Product("pera", 2f, "doces", true, categoria);
        ProductList list = new ProductList(user);
        ProductListItem p2 = new ProductListItem(1, list, x2);
        when(prodService.getById(1)).thenReturn(java.util.Optional.of(x2));
        when(cartService.updateCartItem(user,x2,1)).thenReturn(java.util.Optional.of(p2));
        RestAssuredMockMvc.given()
                .contentType("application/json")
                .when()
                .post("/cart?product=1&amount=1")
                .then()
                .statusCode(201).and().
                body("product.name", equalTo("pera")).
                body("amount", equalTo(1)).
                body("list.user.name", equalTo("Alexandre"));
        verify(userService, times(1)).getAuthenticatedUser();
        verify(cartService, times(1)).updateCartItem(user,x2,1);             
    }

    @Test
    void testBadArgsPostItemInCart_thenReturnBadArgs(){
        User user= new User("alex20002011@gmail.com", "Alexandre", "pass",LocalDate.of(2000, 06, 28), "910123433", false, false);
        when(userService.getAuthenticatedUser()).thenReturn(Optional.of(user));
        List<ProductListItem> ret = new ArrayList<>();
        Category categoria = new Category("FRUTA", true);
        Product x2 = new Product("pera", 2f, "doces", true, categoria);
        ProductList list = new ProductList(user);
        ProductListItem p2 = new ProductListItem(1, list, x2);
        when(prodService.getById(1)).thenReturn(java.util.Optional.of(x2));
        when(cartService.updateCartItem(user,x2,5)).thenReturn(java.util.Optional.of(p2));
        RestAssuredMockMvc.given()
                .contentType("application/json")
                .when()
                .post("/cart/")
                .then()
                .statusCode(400);             
    }


    @Test
    void testPostItemInCart_thenReturnBadUser(){
        User user= new User("alex20002011@gmail.com", "Alexandre", "pass",LocalDate.of(2000, 06, 28), "910123433", false, false);
        when(userService.getAuthenticatedUser()).thenReturn(Optional.empty());
        List<ProductListItem> ret = new ArrayList<>();
        Category categoria = new Category("FRUTA", true);
        Product x2 = new Product("pera", 2f, "doces", true, categoria);
        ProductList list = new ProductList(user);
        ProductListItem p2 = new ProductListItem(1, list, x2);
        when(prodService.getById(1)).thenReturn(java.util.Optional.of(x2));
        when(cartService.updateCartItem(user,x2,5)).thenReturn(java.util.Optional.of(p2));
        RestAssuredMockMvc.given()
                .contentType("application/json")
                .when()
                .post("/cart?product=1&amount=1")
                .then()
                .statusCode(404);             
    }


    @Test
    void testPostItemInCart_thenReturnBadProduct(){
        User user= new User("alex20002011@gmail.com", "Alexandre", "pass",LocalDate.of(2000, 06, 28), "910123433", false, false);
        when(userService.getAuthenticatedUser()).thenReturn(Optional.of(user));
        List<ProductListItem> ret = new ArrayList<>();
        Category categoria = new Category("FRUTA", true);
        Product x2 = new Product("pera", 2f, "doces", true, categoria);
        ProductList list = new ProductList(user);
        ProductListItem p2 = new ProductListItem(1, list, x2);
        when(prodService.getById(1)).thenReturn(Optional.empty());
        when(cartService.updateCartItem(user,x2,5)).thenReturn(java.util.Optional.of(p2));
        RestAssuredMockMvc.given()
                .contentType("application/json")
                .when()
                .post("/cart?product=1&amount=1")
                .then()
                .statusCode(404);             
    }


}