package com.example.demo.model;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import com.example.demo.Models.CartList;
import com.example.demo.Models.Category;
import com.example.demo.Models.OrderList;
import com.example.demo.Models.OrderProductItem;
import com.example.demo.Models.Product;
import com.example.demo.Models.ProductList;
import com.example.demo.Models.ProductListItem;
import com.example.demo.Models.SavedList;
import com.example.demo.Models.User;

import org.junit.jupiter.api.Test;
public class ProductListTest {
    @Test
    void testWhenCreateValidProductListConstuctorDefaultThenReturnProductList(){
        ProductList list = new ProductList();
        list.setId(1L);
        list.setUser(new User());
        list.setSavedList(new SavedList());
        list.setCartList( new CartList());
        list.setOrderList(new OrderList());
        Set<ProductListItem> productListItems = new HashSet<>();
        list.setProductListItems(productListItems);
        assertEquals(1L, list.getId());
        assertEquals(0, list.getUser().getId());
        assertEquals(null, list.getOrderList().getId());
        assertEquals(null, list.getSavedList().getId());
        assertEquals(0, list.getProductListItems().size());
    }

    @Test
    void testWhenCreateValidProductListConstuctorThenReturnProductList(){
        Set<ProductListItem> productListItems = new HashSet<>();
        ProductList list = new ProductList(productListItems, new User(), new CartList(), new SavedList(), new OrderList());
        list.setId(1L);
        list.setProductListItems(productListItems);
        assertEquals(1L, list.getId());
        assertEquals(0, list.getUser().getId());
        assertEquals(null, list.getOrderList().getId());
        assertEquals(null, list.getSavedList().getId());
        assertEquals(0, list.getProductListItems().size());
        
    }

}
