package com.example.demo.model;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.example.demo.Models.Category;
import com.example.demo.Models.OrderProductItem;
import com.example.demo.Models.Product;
import com.example.demo.Models.ProductList;
import com.example.demo.Models.ProductListItem;
import com.example.demo.Models.SavedList;
import com.example.demo.Models.User;
import com.example.demo.Models.UserAddress;

import org.junit.jupiter.api.Test;
public class UserTest {
    @Test
    void testWhenCreateValidUserDefaultConstructorThenReturnUser(){
        User user = new User();
        user.setAdmin(false);
        user.setEmail("alex200020011@gmail.com");
        user.setId(1);
        user.setBirthday(new Date(2000, 5, 28));
        user.setName("Serras");
        user.setPassword("aaaaa");
        user.setStuff(true);
        user.setPhoneNumber("911912912");
        Set<ProductList> productList = new HashSet<>();
		user.setProductList(productList);
        Set<UserAddress> userAddress=new HashSet<>();
		user.setUserAddress(userAddress);
        assertEquals(1L, user.getId());
        assertEquals("Serras", user.getName());
        assertEquals(false, user.getAdmin());
        assertEquals(true, user.getStuff());
        assertEquals("aaaaa", user.getPassword());
        assertEquals("911912912", user.getPhoneNumber());
        assertEquals(5, user.getBirthday().getMonth());
        assertEquals("alex200020011@gmail.com", user.getEmail());
        assertEquals(0, user.getProductList().size());
        assertEquals(0, user.getUserAddress().size());
    }
    @Test
    void testWhenCreateValidUserConstructorThenReturnUser(){
        Set<ProductList> productList = new HashSet<>();
        Set<UserAddress> userAddress=new HashSet<>();
        
        User user = new User("alex200020011@gmail.com", "Serras", "aaaaa", new Date(2000, 5, 28), "911912912", false, true, productList, userAddress);
        user.setId(1);
        assertEquals(1L, user.getId());
        assertEquals("Serras", user.getName());
        assertEquals(false, user.getAdmin());
        assertEquals(true, user.getStuff());
        assertEquals("aaaaa", user.getPassword());
        assertEquals("911912912", user.getPhoneNumber());
        assertEquals(5, user.getBirthday().getMonth());
        assertEquals("alex200020011@gmail.com", user.getEmail());
        assertEquals(0, user.getProductList().size());
        assertEquals(0, user.getUserAddress().size());
    }
    
}
