package com.example.demo.model;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import com.example.demo.Models.Address;
import com.example.demo.Models.CartList;
import com.example.demo.Models.ProductList;
import com.example.demo.Models.UserAddress;

import org.junit.jupiter.api.Test;
public class CartListTest {
    @Test
    void testWhenCreateValidCartListWithDefaultConstuctorThenReturnCartList(){
        CartList cart = new CartList();
        ProductList productList= new ProductList();
        productList.setId(1);
        cart.setProductList(productList);
        assertEquals(1, cart.getProductList().getId());
    }
    @Test
    void testWhenCreateValidCartListWithConstuctorThenReturnCartList(){
        ProductList productList= new ProductList();
        productList.setId(1);
        CartList cart = new CartList(productList);
        assertEquals(1, cart.getProductList().getId());
    }
}
