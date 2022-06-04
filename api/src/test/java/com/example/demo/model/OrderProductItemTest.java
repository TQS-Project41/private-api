package com.example.demo.model;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.demo.models.ListItemId;
import com.example.demo.models.OrderList;
import com.example.demo.models.OrderProductItem;
import com.example.demo.models.Product;

import org.junit.jupiter.api.Test;
public class OrderProductItemTest {
    

    @Test
    void testWhenCreateValidOrderProductItemWithConstuctorDefaultThenReturnOrderProductItem(){
        OrderProductItem x = new OrderProductItem();
        OrderList  lst =new OrderList();
        Product product = new Product();
        product.setId(1L);
        x.setId(new ListItemId(1L, 1L));
        x.setOrderList(lst);
        lst.setId(1L);
        x.setPrice(5.1);
        x.setProduct(product);
        assertEquals(1L, x.getId().getListId());
        assertEquals(1L, x.getId().getProductId());
        assertEquals(5.1, x.getPrice());

    }
    @Test
    void testWhenCreateValidOrderProductItemWithConstuctorThenReturnOrderProductItem(){
        OrderList  lst =new OrderList();
        Product product = new Product();
        product.setId(1L);
        lst.setId(1L);
        OrderProductItem x = new OrderProductItem(5.1, lst, product);
        assertEquals(1L, x.getId().getListId());
        assertEquals(1L, x.getId().getProductId());
        assertEquals(5.1, x.getPrice());

    }
}
