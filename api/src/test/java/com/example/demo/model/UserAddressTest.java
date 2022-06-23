package com.example.demo.model;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import com.example.demo.models.Address;
import com.example.demo.models.User;
import com.example.demo.models.UserAddress;

import org.junit.jupiter.api.Test;
public class UserAddressTest {
    @Test
    void testWhenCreateValidUserAddressDefaultConstructorThenReturnUserAddress(){
        User user = new User();
        user.setId(1);
        user.setEmail("alex200020011@gmail.com");
        user.setBirthday(LocalDate.of(2000, 5, 28));
        user.setName("Serras");
        user.setPassword("aaaaa");
        user.setPhoneNumber("911912912");
        user.setStaff(true);
        user.setAdmin(false);

        Address address = new Address();
        address.setId(1L);

        UserAddress userAddress = new UserAddress();
        userAddress.setAddress(address);
        userAddress.setUser(user);

        assertEquals(null, userAddress.getAddress().getCity());
        assertEquals("Serras", userAddress.getUser().getName());
    }

    @Test
    void testWhenCreateValidUserAddressConstructorThenReturnUserAddress(){
        User user = new User();
        user.setAdmin(false);
        user.setEmail("alex200020011@gmail.com");
        user.setId(1);
        user.setBirthday(LocalDate.of(2000, 5, 28));
        user.setName("Serras");
        user.setPassword("aaaaa");
        user.setStaff(true);
        user.setPhoneNumber("911912912");
        UserAddress userA = new UserAddress(user, new Address());

        assertEquals(null, userA.getAddress().getCity());
        assertEquals("Serras", userA.getUser().getName());
    }
    
}
