package com.example.demo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.demo.models.Category;

import org.junit.jupiter.api.Test;
public class CategoryTest {
    @Test
    void testWhenCreateValidCategoryWithDefaultConstuctorThenReturnCategory(){
        Category cat = new Category();
        cat.setActive(false);
        cat.setId(1);
        cat.setName("Vegetais");

        assertEquals(1, cat.getId());
        assertEquals("Vegetais", cat.getName());
        assertEquals(false, cat.getActive());
    }
    @Test
    void testWhenCreateValidCategoryWithConstuctorThenReturnCategory(){
        Category cat = new Category("Vegetais", false);

        assertEquals("Vegetais", cat.getName());
        assertEquals(false, cat.getActive());
    }

    @Test
    void testWhenCreateValidCategoryWithConstuctorv2ThenReturnCategory(){
        Category cat = new Category("Vegetais", false);

        assertEquals("Vegetais", cat.getName());
        assertEquals(false, cat.getActive());
    }

}
