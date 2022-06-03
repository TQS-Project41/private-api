package com.example.demo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.demo.models.OrderProductItemId;

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
