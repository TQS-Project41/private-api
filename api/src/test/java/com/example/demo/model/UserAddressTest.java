package com.example.demo.model;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.example.demo.Models.Address;
import com.example.demo.Models.Category;
import com.example.demo.Models.OrderProductItem;
import com.example.demo.Models.Product;
import com.example.demo.Models.ProductList;
import com.example.demo.Models.ProductListItem;
import com.example.demo.Models.SavedList;
import com.example.demo.Models.User;
import com.example.demo.Models.UserAddress;
import com.example.demo.Models.UserAddressID;

import org.junit.jupiter.api.Test;
public class UserAddressTest {
    @Test
    void testWhenCreateValidUserAddressDefaultConstructorThenReturnUserAddress(){
        UserAddress userA = new UserAddress();
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
        userA.setUser(user);
        userA.setAddress(new Address());
        userA.setId(new UserAddressID(1L, 1L));
        assertEquals(1L, userA.getId().getAddressId());
        assertEquals(1L, userA.getId().getUserId());
        assertEquals(null, userA.getAddress().getCity());
        assertEquals("Serras", userA.getUser().getName());
    }

    @Test
    void testWhenCreateValidUserAddressConstructorThenReturnUserAddress(){
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
        UserAddress userA = new UserAddress(user, new Address());

        assertEquals(0, userA.getId().getAddressId());
        assertEquals(1L, userA.getId().getUserId());
        assertEquals(null, userA.getAddress().getCity());
        assertEquals("Serras", userA.getUser().getName());
    }
    
}
