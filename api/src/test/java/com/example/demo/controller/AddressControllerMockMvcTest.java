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

import com.example.demo.config.WebSecurityConfig;
import com.example.demo.models.Address;
import com.example.demo.service.AddressService;
import static org.hamcrest.Matchers.*;
import org.springframework.context.annotation.FilterType;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

@WebMvcTest(value = AddressController.class, excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = WebSecurityConfig.class)})
@AutoConfigureMockMvc(addFilters = false)
public class AddressControllerMockMvcTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private AddressService addressService;

    @BeforeEach
    void setUp()  {
        RestAssuredMockMvc.mockMvc(mvc);
    }


    @Test
    void testGetAddressbyId_thenReturnAddress(){
        Address address2 = new Address("Portugal", "1201-222", "Aveiro", "Rua das Estia");
        address2.setId(1L);

        when(addressService.getById(1)).thenReturn(address2);

        RestAssuredMockMvc.given()
                .contentType("application/json")
                .when()
                .get("/addresses/address/{id}",1)
                .then()
                .statusCode(200).and().
                body("city", equalTo("Aveiro")).
                body("address", equalTo("Rua das Estia"));
                verify(addressService, times(1)).getById(1);
    }

    @Test
    void testGetAddressbyInvalidId_thenReturnBadRequest(){
        Address address2 = new Address("Portugal", "1201-222", "Aveiro", "Rua das Estia");
        address2.setId(1L);

        when(addressService.getById(1)).thenReturn(address2);

        RestAssuredMockMvc.given()
                .contentType("application/json")
                .when()
                .get("/addresses/address/{id}",2)
                .then()
                .statusCode(400);
                verify(addressService, times(0)).getById(1);
    }
}
