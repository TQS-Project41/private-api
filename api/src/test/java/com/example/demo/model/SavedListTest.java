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
        assertEquals(null, list.getProductList().getOrderList());
    }

    @Test
    void testWhenCreateValidSavedListConstructorThenReturnSavedList(){
        SavedList list = new SavedList(new ProductList(), "Pedrofilo");
        list.setId(1L);
        assertEquals(1L, list.getId());
        assertEquals("Pedrofilo", list.getName());
        assertEquals(null, list.getProductList().getOrderList());
    }
}
