package com.example.demo.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.example.demo.config.WebSecurityConfig;
import com.example.demo.models.Category;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ProductService;
import static org.hamcrest.Matchers.*;
import org.springframework.context.annotation.FilterType;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

@WebMvcTest(value = CategoryController.class, excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = WebSecurityConfig.class)})
@AutoConfigureMockMvc(addFilters = false)
public class CategoryControllerMockMvcTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private CategoryService categoryService;

   


    @BeforeEach
    void setUp()  {
        RestAssuredMockMvc.mockMvc(mvc);
    }


    @Test
    void whenGetCategories_thenReturnAllCategories( ) throws Exception {
        List<Category> ret  = new ArrayList<>();
        ret.add(new Category("Legumes", true));
        ret.add(new Category("Fruta", true));
        ret.add(new Category("Doces", false));

        when(categoryService.getAll()).thenReturn(ret);
    
        RestAssuredMockMvc.given().contentType(ContentType.JSON)
        .when().get("/categories/category/").then().assertThat().statusCode(200).and().
        body("[0].name", equalTo("Legumes")).and().
        body("[1].name", equalTo("Fruta")).and().
        body("[2].name", equalTo("Doces")).and().
        body("[0].active", equalTo(true)).and().
        body("[1].active", equalTo(true)).and().
        body("[2].active", equalTo(false));
        
       

        verify(categoryService, times(1)).getAll();

    }

    @Test
    void whenPostInvalidCategory_thenFailCreate( ) throws Exception {
        Category res = new Category("legumes",true);
        when(categoryService.save(Mockito.any())).thenReturn(res);
        List<Category> ret  = new ArrayList<>();
        ret.add(new Category("Legumes", true));
        ret.add(new Category("Fruta", true));
        ret.add(new Category("Doces", false));
        when(categoryService.getAll()).thenReturn(ret);
        RestAssuredMockMvc.given().
        contentType(ContentType.JSON)
        .when().post("/categories/category?name=legumes").then().assertThat().statusCode(400);
        verify(categoryService, times(1)).getAll();

    }

    @Test
    void whenPostInvalidCategory_thenFailUpdate( ) throws Exception {
        Category res = new Category("legumes",true);
        when(categoryService.save(Mockito.any())).thenReturn(res);
        List<Category> ret  = new ArrayList<>();
        ret.add(new Category("Legumes", true));
        ret.add(new Category("Fruta", true));
        ret.add(new Category("Doces", false));
        when(categoryService.getAll()).thenReturn(ret);
        RestAssuredMockMvc.given().
        contentType(ContentType.JSON).
        when().put("/categories/category/{id}?name=aaaa",5).then().assertThat().statusCode(404);
        verify(categoryService, times(1)).getAll();
    }

    @Test
    void whenPostInvalidCategory_thenFailUpdate2( ) throws Exception {
        Category res = new Category("legumes",true);
        when(categoryService.save(Mockito.any())).thenReturn(res);
        List<Category> ret  = new ArrayList<>();
        Category a = new Category("Legumes", true);
        a.setId(1);
        ret.add(a);
        ret.add(new Category("Fruta", true));
        ret.add(new Category("Doces", false));
        when(categoryService.getAll()).thenReturn(ret);
        RestAssuredMockMvc.given().
        contentType(ContentType.JSON).
        when().put("/categories/category/{id}?name=fruta",1).then().assertThat().statusCode(404);
        verify(categoryService, times(1)).getAll();
    }

    @Test
    void whenPostInvalidCategory_thenUpdate( ) throws Exception {
        Category res = new Category("legumes",true);
        when(categoryService.save(Mockito.any())).thenReturn(res);
        List<Category> ret  = new ArrayList<>();
        Category a = new Category("Legumes", true);
        a.setId(1);
        ret.add(a);
        ret.add(new Category("Fruta", true));
        ret.add(new Category("Doces", false));
        when(categoryService.getAll()).thenReturn(ret);
        RestAssuredMockMvc.given().
        contentType(ContentType.JSON).
        when().put("/categories/category/{id}?name=aaaa",1).then().assertThat().statusCode(200);
        verify(categoryService, times(1)).getAll();
    }

    @Test
    void whenINvalidCategory_then404Return( ) throws Exception {
        Category res = new Category("legumes",true);
        when(categoryService.save(Mockito.any())).thenReturn(res);
        List<Category> ret  = new ArrayList<>();
        ret.add(new Category("Legumes", true));
        ret.add(new Category("Fruta", true));
        ret.add(new Category("Doces", false));
        when(categoryService.getAll()).thenReturn(ret);
        RestAssuredMockMvc.given().
        contentType(ContentType.JSON).
        when().delete("/categories/category/{id}",5).then().assertThat().statusCode(404);
        verify(categoryService, times(1)).getAll();
    }

    @Test
    void whenDeleteCategory_thenDeleted( ) throws Exception {
        Category res = new Category("legumes",true);
        when(categoryService.save(Mockito.any())).thenReturn(res);
        List<Category> ret  = new ArrayList<>();
        Category a = new Category("Legumes", true);
        a.setId(1);
        ret.add(a);
        ret.add(new Category("Fruta", true));
        ret.add(new Category("Doces", false));
        when(categoryService.getAll()).thenReturn(ret);
        RestAssuredMockMvc.given().
        contentType(ContentType.JSON).
        when().delete("/categories/category/{id}",1).then().assertThat().statusCode(200);
        verify(categoryService, times(1)).getAll();
    }


    @Test
    void whenPostCategory_thenCreateCategory( ) throws Exception {
        Category res = new Category("legumes",true);

        when(categoryService.save(Mockito.any())).thenReturn(res);
        RestAssuredMockMvc.given().
        contentType(ContentType.JSON)
        .when().post("/categories/category?name=legumes").then().assertThat().statusCode(201).and().
        body("name", equalTo("legumes")).and()
        .body("active", equalTo(true));
        verify(categoryService, times(1)).save(Mockito.any());

    }
}
