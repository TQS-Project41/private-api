package com.example.demo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.demo.Models.Category;
import com.example.demo.Models.Product;

import org.junit.jupiter.api.Test;


public class ProductTest {


    @Test
    void testWhenCreateValidProductConstuctorDefaultThenReturnProduct(){
        Product product = new Product();
        product.setId(1L);
        product.setDescription("leve");
        product.setName("Pilhas");
        product.setPrice(5.1f);
        product.setIsActive(true);
        product.setCategory(new Category());
        assertEquals(1L, product.getId());
        assertEquals("leve", product.getDescription());
        assertEquals("Pilhas", product.getName());
        assertEquals(true, product.getIsActive());
        assertEquals(5.1f, product.getPrice());
        assertEquals(0, product.getCategory().getId());
        assertEquals(0, product.getCategory().getProduct().size());
    }


    @Test
    void testWhenCreateValidProductConstuctorThenReturnProduct(){
        Product product = new Product("Pilhas", 5.1f, "leve", true, new Category());
        product.setId(1L);
        assertEquals(1L, product.getId());
        assertEquals("leve", product.getDescription());
        assertEquals("Pilhas", product.getName());
        assertEquals(5.1f, product.getPrice());
        assertEquals(true, product.getIsActive());
        assertEquals(0, product.getCategory().getId());
        assertEquals(0, product.getCategory().getProduct().size());
    }
}
