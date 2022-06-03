package com.example.demo.model;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.demo.models.Product;
import com.example.demo.models.ProductList;
import com.example.demo.models.ProductListItem;
import com.example.demo.models.ProductListItemId;

import org.junit.jupiter.api.Test;
public class ProductListItemTest {
    @Test
    void testWhenCreateValidProductListItemConstuctorDefaultThenReturnProductListItem(){
        ProductListItem list = new ProductListItem();
        list.setAmount(5);
        list.setProduct(new Product());
        list.setList(new ProductList());
        list.setId(new ProductListItemId(1L, 1L)  );
        assertEquals(1L, list.getId().getListId());
        assertEquals(1L, list.getId().getProductId());
        assertEquals(5, list.getAmount());
        assertEquals(0, list.getProduct().getId());
        assertEquals(0,list.getList().getId());
    }

    @Test
    void testWhenCreateValidProductListItemDefaultThenReturnProductListItem(){
        Product p = new Product();
        p.setId(1L);
        ProductList pl= new ProductList();
        pl.setId(1L);
        ProductListItem list = new ProductListItem(5,pl , p);
        assertEquals(1L, list.getId().getListId());
        assertEquals(1L, list.getId().getProductId());
        assertEquals(5, list.getAmount());
        assertEquals(1L, list.getProduct().getId());
        assertEquals(1L,list.getList().getId());
    }

}
