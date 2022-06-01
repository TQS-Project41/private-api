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
public class OrderProductItemTest {
    

    @Test
    void testWhenCreateValidOrderProductItemWithConstuctorDefaultThenReturnOrderProductItem(){
        OrderProductItem x = new OrderProductItem();
        OrderList  lst =new OrderList();
        Product product = new Product();
        product.setId(1L);
        x.setId(new OrderProductItemId(1L, 1L));
        x.setOrderList(lst);
        lst.setId(1L);
        x.setPrice(5.1);
        x.setProduct(product);
        assertEquals(1L, x.getId().getOrderListId());
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
        assertEquals(1L, x.getId().getOrderListId());
        assertEquals(1L, x.getId().getProductId());
        assertEquals(5.1, x.getPrice());

    }
}
