package com.example.demo.controller;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.models.Address;
import com.example.demo.models.Store;
import com.example.demo.security.AuthTokenFilter;
import com.example.demo.security.JwtUtils;
import com.example.demo.security.WebSecurityConfig;
import com.example.demo.service.StoreService;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import java.util.ArrayList;
import java.util.List;

import org.mockito.Mockito;
import org.springframework.context.annotation.ComponentScan;

import com.example.demo.service.AddressService;
import static org.hamcrest.Matchers.*;
import org.springframework.context.annotation.FilterType;


@WebMvcTest(value = StoreController.class, excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = WebSecurityConfig.class)})
@AutoConfigureMockMvc(addFilters = false)
public class StoreControllerMockMvcTest {
    
    @Autowired
    private MockMvc mvc;

    @MockBean
    private StoreService storeService;

    @MockBean
    private AddressService addressService;

    @MockBean
    private JwtUtils jwtUtils;

    @MockBean
    private AuthTokenFilter authTokenFilter;
    
    @BeforeEach
    void setUp()  {
        RestAssuredMockMvc.mockMvc(mvc);
    }
    
    @Test
    void testGetAllStores_thenReturnAllStore(){
        Address address = new Address("Portugal", "1201-222", "Aveiro", "Rua das POmbas");
        Address address2 = new Address("Portugal", "1201-222", "Aveiro", "Rua das Estia");
        Store a = new Store("Puma", address);
        Store b = new Store("NIKE", address2);
        List<Store> ret = new ArrayList<>();

        ret.add(a);
        ret.add(b);

        when(storeService.getAll()).thenReturn(ret);

        RestAssuredMockMvc.given()
                .contentType("application/json")
                .when()
                .get("/stores")
                .then()
                .statusCode(200).and().
                body("[0].name", equalTo("Puma")).
                body("[0].address.city", equalTo("Aveiro")).
                body("[1].name", equalTo("NIKE"));
                verify(storeService, times(1)).getAll();
    }


    @Test
    void testCreateStore_thenReturnStore(){
        Address address2 = new Address("Portugal", "1201-222", "Aveiro", "Rua das Estia");
        Store a = new Store("Puma", address2);

        when(addressService.save(Mockito.any())).thenReturn(address2);
        when(storeService.save(Mockito.any())).thenReturn(a);

        RestAssuredMockMvc.given()
                .contentType("application/json")
                .when()
                .post("/stores?name=Puma&country=Portugal&zipcode=1201-222&city=Aveiro&address=Rua das Estia")
                .then()
                .statusCode(201).and().
                body("name", equalTo("Puma")).
                body("address.city", equalTo("Aveiro")).
                body("address.country", equalTo("Portugal"));
                verify(storeService, times(1)).save(Mockito.any());
    }

    @Test
    void testCreateInvalidStore_thenReturnError(){
        Address address2 = new Address("Portugal", "1201-222", "Aveiro", "Rua das Estia");
        Store a = new Store("Puma", address2);

        when(addressService.save(Mockito.any())).thenReturn(address2);
        when(storeService.save(Mockito.any())).thenReturn(a);

        RestAssuredMockMvc.given()
                .contentType("application/json")
                .when()
                .post("/stores?country=Portugal&zipcode=1201-222&city=Aveiro&address=Rua das Estia")
                .then()
                .statusCode(400);
    }


    @Test
    void testGetStorebyId_thenReturnStore(){
        Address address2 = new Address("Portugal", "1201-222", "Aveiro", "Rua das Estia");
        Store b = new Store("NIKE", address2);
        b.setId(1L);

        when(storeService.getById(1)).thenReturn(b);

        RestAssuredMockMvc.given()
                .contentType("application/json")
                .when()
                .get("/stores/{id}",1)
                .then()
                .statusCode(200).and().
                body("address.city", equalTo("Aveiro")).
                body("name", equalTo("NIKE"));
                verify(storeService, times(1)).getById(1);
    }

    @Test
    void testGetStorebyInvalidId_thenReturnBadRequest(){
        Address address2 = new Address("Portugal", "1201-222", "Aveiro", "Rua das Estia");
        Store b = new Store("NIKE", address2);
        b.setId(1L);

        when(storeService.getById(1)).thenReturn(b);

        RestAssuredMockMvc.given()
                .contentType("application/json")
                .when()
                .get("/stores/{id}",2)
                .then()
                .statusCode(400);
                verify(storeService, times(0)).getById(1);
    }

    @Test
    void testUpdateStorebyInvalidId_thenReturnBadRequest(){
        Address address2 = new Address("Portugal", "1201-222", "Aveiro", "Rua das Estia");
        Store b = new Store("NIKE", address2);
        b.setId(1L);

        when(storeService.getById(1)).thenReturn(b);

        RestAssuredMockMvc.given()
                .contentType("application/json")
                .when()
                .put("/stores/{id}?name=aaaa",2)
                .then()
                .statusCode(400);
                verify(storeService, times(0)).getById(1);
    }

    @Test
    void testUpdateStorebyIdthenReturnStore(){
        Address address2 = new Address("Portugal", "1201-222", "Aveiro", "Rua das Estia");
        Store b = new Store("NIKE", address2);
        b.setId(1L);

        when(storeService.getById(1)).thenReturn(b);

        RestAssuredMockMvc.given()
                .contentType("application/json")
                .when()
                .put("/stores/{id}?name=puma",1)
                .then()
                .statusCode(200).and().
                body("name", equalTo("puma"));
                verify(storeService, times(1)).getById(1);
    }


}
