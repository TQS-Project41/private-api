package com.example.demo.controller;


import static org.mockito.Mockito.*;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.models.Category;
import com.example.demo.models.Product;
import com.example.demo.security.AuthTokenFilter;
import com.example.demo.security.JwtUtils;
import com.example.demo.security.WebSecurityConfig;
import com.example.demo.service.ProductService;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import java.util.ArrayList;
import java.util.List;

import org.mockito.Mockito;
import org.springframework.context.annotation.ComponentScan;

import com.example.demo.service.CategoryService;
import static org.hamcrest.Matchers.*;
import org.springframework.context.annotation.FilterType;

import io.restassured.http.ContentType;

@WebMvcTest(value = ProductController.class, excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = WebSecurityConfig.class)})
@AutoConfigureMockMvc(addFilters = false)
public class ProductControllerMockMvcTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private JwtUtils jwtUtils;

    @MockBean
    private AuthTokenFilter authTokenFilter;
    
    @BeforeEach
    void setUp()  {
        RestAssuredMockMvc.mockMvc(mvc);
    }

    @Test
    void testGetAllProducts_thenReturnEmptyList(){
        RestAssuredMockMvc.given()
                .contentType("application/json")
                .when()
                .get("/products")
                .then()
                .statusCode(200);
                
        verify(productService, times(1)).getAll(Mockito.eq(null), Mockito.eq(""), Mockito.eq(0f), Mockito.eq(null), Mockito.any(Pageable.class) );

    }

    @Test
    void testGetAllProducts_thenReturnAllProducts(){
        Category a = new Category("Legumes", true);
        Product p1 = new Product("tomate", 5.0f, "frescos", true, a);
        Product p2 = new Product("cebola",1.5f,"colhidas hoje",true,a);
        Pageable pageable = Pageable.unpaged();
        Page<Product> ret= new PageImpl<>(Arrays.asList(p1, p2), pageable, 2);
        when(productService.getAll(Mockito.eq(null), Mockito.eq(""), Mockito.eq(0f), Mockito.eq(null), Mockito.any(Pageable.class) )).thenReturn(ret);

        RestAssuredMockMvc.given()
                .contentType("application/json")
                .when()
                .get("/products")
                .then()
                .statusCode(200).and().
                body("content.name", hasItems("tomate","cebola"));

        verify(productService, times(1)).getAll(Mockito.eq(null), Mockito.eq(""), Mockito.eq(0f), Mockito.eq(null), Mockito.any(Pageable.class) );

    }
    

    @Test
    void whenPostProduct_thenCreateProduct( ) throws Exception {
        Category res = new Category("legumes",true);
        List<Category> lst = new ArrayList<>();
        lst.add(res);
        Product prod= new Product("tomate", 5.0f, "frescos", true, res);
        when(productService.save(Mockito.any())).thenReturn(prod);
        when(categoryService.getAll()).thenReturn(lst);

        RestAssuredMockMvc.given().
        contentType(ContentType.JSON)
        .when().post("/products?name=tomate&description=frescos&price=5.0&category=0").then().assertThat().statusCode(201).and().
        body("name", equalTo("tomate")).and()
        .body("isActive", equalTo(true));
        verify(productService, times(1)).save(Mockito.any());
    }
    @Test
    void whenPostInvalidProduct_thenReturnError( ) throws Exception {
        Category res = new Category("legumes",true);
        List<Category> lst = new ArrayList<>();
        lst.add(res);
        Product prod= new Product("tomate", 5.0f, "frescos", true, res);
        when(productService.save(Mockito.any())).thenReturn(prod);
        when(categoryService.getAll()).thenReturn(lst);

        RestAssuredMockMvc.given().
        contentType(ContentType.JSON)
        .when().post("/products?name=tomate&description=frescos&price=5.0&category=1").then().assertThat().statusCode(404);
        verify(productService, times(0)).save(Mockito.any());

    }

    @Test
    void whengetProduct_thenCreateProduct( ) throws Exception {
        Category res = new Category("legumes",true);
        List<Category> lst = new ArrayList<>();
        lst.add(res);
        when(productService.getById(1)).thenReturn(
            java.util.Optional.of(new Product("tomate", 5.0f, "frescos", true, res)));
        when(categoryService.getAll()).thenReturn(lst);

        RestAssuredMockMvc.given().
        contentType(ContentType.JSON)
        .when().get("/products/{id}",1).then().assertThat().statusCode(200).and().
        body("name", equalTo("tomate")).and()
        .body("isActive", equalTo(true));
        verify(productService, times(1)).getById(1);
    }

    @Test
    void whengetInvalidIDProduct_thenCreateProduct( ) throws Exception {
        Category res = new Category("legumes",true);
        List<Category> lst = new ArrayList<>();
        lst.add(res);
        when(productService.getById(1)).thenReturn(
            java.util.Optional.of(new Product("tomate", 5.0f, "frescos", true, res)));
        when(categoryService.getAll()).thenReturn(lst);

        RestAssuredMockMvc.given().
        contentType(ContentType.JSON)
        .when().get("/products/{id}",0).then().assertThat().statusCode(404);
    }

    @Test
    void whengetInvalidIDProduct_thenDeleteFails( ) throws Exception {
        Category res = new Category("legumes",true);
        List<Category> lst = new ArrayList<>();
        lst.add(res);
        when(productService.getById(1)).thenReturn(
            java.util.Optional.of(new Product("tomate", 5.0f, "frescos", true, res)));
        when(categoryService.getAll()).thenReturn(lst);

        RestAssuredMockMvc.given().
        contentType(ContentType.JSON)
        .when().delete("/products/{id}",0).then().assertThat().statusCode(404);
    }

    @Test
    void whenDeleteProduct_thenDeleteProduct( ) throws Exception {
        Category res = new Category("legumes",true);
        List<Category> lst = new ArrayList<>();
        lst.add(res);
        when(productService.getById(1)).thenReturn(
            java.util.Optional.of(new Product("tomate", 5.0f, "frescos", true, res)));
        when(categoryService.getAll()).thenReturn(lst);
        RestAssuredMockMvc.given().
        contentType(ContentType.JSON)
        .when().delete("/products/{id}",1).then().assertThat().statusCode(200).and().
        body("name", equalTo("tomate")).and()
        .body("isActive", equalTo(false));
        verify(productService, times(1)).getById(1);
    }

    @Test
    void whenUpdateProduct_thenUpdateProduct( ) throws Exception {
        Category res = new Category("legumes",true);
        List<Category> lst = new ArrayList<>();
        lst.add(res);
        when(productService.getById(1)).thenReturn(
            java.util.Optional.of(new Product("tomate", 5.0f, "frescos", true, res)));
        when(categoryService.getAll()).thenReturn(lst);
        RestAssuredMockMvc.given().
        contentType(ContentType.JSON)
        .when().put("/products/{id}?name=aaa",1).then().assertThat().statusCode(200).and().
        body("name", equalTo("aaa")).and()
        .body("isActive", equalTo(true));
        verify(productService, times(1)).getById(1);
    }

    @Test
    void whenUpdateInvalidProduct_thenNotFound( ) throws Exception {
        Category res = new Category("legumes",true);
        List<Category> lst = new ArrayList<>();
        lst.add(res);
        when(productService.getById(1)).thenReturn(
            java.util.Optional.of(new Product("tomate", 5.0f, "frescos", true, res)));
        when(categoryService.getAll()).thenReturn(lst);
        RestAssuredMockMvc.given().
        contentType(ContentType.JSON)
        .when().put("/products/{id}?name=aaa",0).then().assertThat().statusCode(404);
        verify(productService, times(0)).getById(1);
    }
    @Test
    void whenUpdateCategoryProduct_thenProduct( ) throws Exception {
        Category res = new Category("legumes",true);
        Category res1 = new Category("fruta",true);
        res1.setId(1L);
        List<Category> lst = new ArrayList<>();
        lst.add(res);
        lst.add(res1);

        when(productService.getById(1)).thenReturn(
            java.util.Optional.of(new Product("tomate", 5.0f, "frescos", true, res)));
        when(categoryService.getAll()).thenReturn(lst);
        RestAssuredMockMvc.given().
        contentType(ContentType.JSON)
        .when().put("/products/{id}?name=aaa&category=1",1).then().assertThat().statusCode(200).and().
        body("name", equalTo("aaa")).and().
        body("category.id", equalTo(1)).and().
        body("category.name", equalTo("fruta")).and()
        .body("isActive", equalTo(true));  
        verify(productService, times(1)).getById(1);

  }


  @Test
    void whenUpdateInvalidCategoryProduct_thenNotFoundProduct( ) throws Exception {
        Category res = new Category("legumes",true);
        Category res1 = new Category("fruta",true);
        res1.setId(1L);
        List<Category> lst = new ArrayList<>();
        lst.add(res);
        lst.add(res1);

        when(productService.getById(1)).thenReturn(
            java.util.Optional.of(new Product("tomate", 5.0f, "frescos", true, res)));
        when(categoryService.getAll()).thenReturn(lst);
        RestAssuredMockMvc.given().
        contentType(ContentType.JSON)
        .when().put("/products/{id}?name=aaa&category=2",1).then().assertThat().statusCode(404);
        verify(productService, times(0)).getById(1);

  }
}
