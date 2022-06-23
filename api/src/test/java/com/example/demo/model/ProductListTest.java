package com.example.demo.model;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.demo.models.ProductList;
import com.example.demo.models.User;

import org.junit.jupiter.api.Test;
public class ProductListTest {
    @Test
    void testWhenCreateValidProductListConstuctorDefaultThenReturnProductList(){
        ProductList list = new ProductList();
        list.setId(1L);
        list.setUser(new User());
        assertEquals(1L, list.getId());
        assertEquals(0, list.getUser().getId());
    }

    @Test
    void testWhenCreateValidProductListConstuctorThenReturnProductList(){
        ProductList list = new ProductList(new User());
        list.setId(1L);
        assertEquals(1L, list.getId());
        assertEquals(0, list.getUser().getId());        
    }

}
