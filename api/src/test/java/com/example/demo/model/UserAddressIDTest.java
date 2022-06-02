package com.example.demo.model;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import com.example.demo.Models.Category;
import com.example.demo.Models.OrderProductItem;
import com.example.demo.Models.Product;
import com.example.demo.Models.ProductList;
import com.example.demo.Models.ProductListItem;
import com.example.demo.Models.SavedList;
import com.example.demo.Models.UserAddressID;

import org.junit.jupiter.api.Test;
public class UserAddressIDTest {
    @Test
    void testWhenCreateValidUserAddressIDDefaultConstructorThenReturnSavedUserAddressID(){
        UserAddressID user = new UserAddressID();
        user.setAddressId(1L);
        user.setUserId(1L);
        assertEquals(1L, user.getAddressId());
        assertEquals(1L, user.getUserId());
    }
    @Test
    void testWhenCreateValidUserAddressIDConstructorThenReturnSavedUserAddressID(){
        UserAddressID user = new UserAddressID(1L,1L);
        assertEquals(1L, user.getAddressId());
        assertEquals(1L, user.getUserId());
    }
}
