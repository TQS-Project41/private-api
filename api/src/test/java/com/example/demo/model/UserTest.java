package com.example.demo.model;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import com.example.demo.models.User;

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
        user.setStaff(true);
        user.setPhoneNumber("911912912");
        assertEquals(1L, user.getId());
        assertEquals("Serras", user.getName());
        assertEquals(false, user.getAdmin());
        assertEquals(true, user.getStaff());
        assertEquals("aaaaa", user.getPassword());
        assertEquals("911912912", user.getPhoneNumber());
        assertEquals(5, user.getBirthday().getMonth());
        assertEquals("alex200020011@gmail.com", user.getEmail());
    }
    @Test
    void testWhenCreateValidUserConstructorThenReturnUser(){        
        User user = new User("alex200020011@gmail.com", "Serras", "aaaaa", new Date(2000, 5, 28), "911912912", false, true);
        user.setId(1);
        assertEquals(1L, user.getId());
        assertEquals("Serras", user.getName());
        assertEquals(false, user.getAdmin());
        assertEquals(true, user.getStaff());
        assertEquals("aaaaa", user.getPassword());
        assertEquals("911912912", user.getPhoneNumber());
        assertEquals(5, user.getBirthday().getMonth());
        assertEquals("alex200020011@gmail.com", user.getEmail());
    }
    
}
