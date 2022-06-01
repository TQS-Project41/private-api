package com.example.demo.model;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import com.example.demo.Models.Address;
import com.example.demo.Models.CartList;
import com.example.demo.Models.Category;
import com.example.demo.Models.Product;
import com.example.demo.Models.ProductList;
import com.example.demo.Models.UserAddress;

import org.junit.jupiter.api.Test;
public class CategoryTest {
    @Test
    void testWhenCreateValidCategoryWithDefaultConstuctorThenReturnCategory(){
        Category cat = new Category();
        cat.setActive(false);
        cat.setId(1);
        cat.setName("Vegetais");
        Set<Product> x = new HashSet();
        cat.setProduct(x);

        assertEquals(1, cat.getId());
        assertEquals("Vegetais", cat.getName());
        assertEquals(false, cat.getActive());
        assertEquals(0, cat.getProduct().size());
    }
    @Test
    void testWhenCreateValidCategoryWithConstuctorThenReturnCategory(){
        Set<Product> x = new HashSet();
        Category cat = new Category("Vegetais", false, x);

        assertEquals("Vegetais", cat.getName());
        assertEquals(false, cat.getActive());
        assertEquals(0, cat.getProduct().size());
    }

    @Test
    void testWhenCreateValidCategoryWithConstuctorv2ThenReturnCategory(){
        Category cat = new Category("Vegetais", false);

        assertEquals("Vegetais", cat.getName());
        assertEquals(false, cat.getActive());
        assertEquals(0, cat.getProduct().size());
    }

}
