package com.example.demo.controller;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.models.Address;
import com.example.demo.security.AuthTokenFilter;
import com.example.demo.security.JwtUtils;
import com.example.demo.security.WebSecurityConfig;
import com.example.demo.service.AddressService;
import com.example.demo.service.UserService;

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

    @MockBean
    private UserService userService;

    @MockBean
    private JwtUtils jwtUtils;

    @MockBean
    private AuthTokenFilter authTokenFilter;

    @BeforeEach
    void setUp()  {
        RestAssuredMockMvc.mockMvc(mvc);
    }

    /*
    @Test
    void testGetAddressbyUser_thenReturnAddress(){
        Address address2 = new Address("Portugal", "1201-222", "Aveiro", "Rua das Estia");
        address2.setId(1L);
        Address address1 = new Address("Portugal", "1111-222", "Aveiro", "Rua daEstia");
        address2.setId(2L);

        List<Address> ret = new ArrayList<>();
        ret.add(address1);
        ret.add(address2);


        User user= new User("alex20002011@gmail.com", "Alexandre", "pass",LocalDate.of(2000, 06, 28), "910123433", false, false);
        

        when(addressService.getAllByUser(user)).thenReturn(ret);
        when((User) authentication.getPrincipal()).thenReturn(address2);


        RestAssuredMockMvc.given()
                .contentType("application/json")
                .when()
                .get("/addresses/{id}",1)
                .then()
                .statusCode(200).and().
                body("city", equalTo("Aveiro")).
                body("address", equalTo("Rua das Estia"));
                verify(addressService, times(1)).getById(1);
    }
     */

    @Test
    void testGetAddressbyId_thenReturnAddress(){
        Address address2 = new Address("Portugal", "1201-222", "Aveiro", "Rua das Estia");
        address2.setId(1L);

        when(addressService.getById(1)).thenReturn(address2);

        RestAssuredMockMvc.given()
                .contentType("application/json")
                .when()
                .get("/addresses/{id}",1)
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
                .get("/addresses/{id}",2)
                .then()
                .statusCode(400);
                verify(addressService, times(0)).getById(1);
    }
}
