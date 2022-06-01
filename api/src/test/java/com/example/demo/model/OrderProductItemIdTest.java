package com.example.demo.model;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import com.example.demo.Models.Address;
import com.example.demo.Models.CartList;
import com.example.demo.Models.Category;
import com.example.demo.Models.OrderList;
import com.example.demo.Models.OrderProductItem;
import com.example.demo.Models.OrderProductItemId;
import com.example.demo.Models.Product;
import com.example.demo.Models.ProductList;
import com.example.demo.Models.Store;
import com.example.demo.Models.UserAddress;

import org.junit.jupiter.api.Test;
public class OrderProductItemIdTest {
    @Test
    void testWhenCreateValidOrderProductItemIdTestWithConstuctorDefaultThenReturnOrderProductItemIdTest(){
        OrderProductItemId x=new OrderProductItemId(1L, 1L);
        assertEquals(1L, x.getOrderListId());
        assertEquals(1L, x.getProductId());

    }

    @Test
    void testWhenCreateValidOrderProductItemIdTestWithDefaultThenReturnOrderProductItemIdTest(){
        OrderProductItemId x=new OrderProductItemId();
        x.setOrderListId(1L);
        x.setProductId(1L);
        assertEquals(1L, x.getOrderListId());
        assertEquals(1L, x.getProductId());

    }
    
}
