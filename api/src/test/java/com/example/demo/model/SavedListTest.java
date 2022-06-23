package com.example.demo.model;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.demo.models.ProductList;
import com.example.demo.models.SavedList;

import org.junit.jupiter.api.Test;
public class SavedListTest {
    @Test
    void testWhenCreateValidSavedListDefaultConstructorThenReturnSavedList(){
        SavedList list = new SavedList();
        list.setId(1L);
        list.setName("Pedrofilo");
        list.setProductList(new ProductList());
        assertEquals(1L, list.getId());
        assertEquals("Pedrofilo", list.getName());
    }

    @Test
    void testWhenCreateValidSavedListConstructorThenReturnSavedList(){
        SavedList list = new SavedList(new ProductList(), "Pedrofilo");
        list.setId(1L);
        assertEquals(1L, list.getId());
        assertEquals("Pedrofilo", list.getName());
    }
}
