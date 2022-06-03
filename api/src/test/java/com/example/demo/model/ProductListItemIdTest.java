package com.example.demo.model;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.demo.models.ProductListItemId;

import org.junit.jupiter.api.Test;
public class ProductListItemIdTest {
    
    @Test
    void testWhenCreateValidProductListItemIdDefaultConstructorThenReturnProductListItemId(){
        ProductListItemId p = new ProductListItemId();
        p.setListId(1L);
        p.setProductId(1L);

        assertEquals(1L, p.getListId());
        assertEquals(1L, p.getProductId());

    }

    @Test
    void testWhenCreateValidProductListItemIdConstructorThenReturnProductListItemId(){
        ProductListItemId p = new ProductListItemId(1L,1L);
    
        assertEquals(1L, p.getListId());
        assertEquals(1L, p.getProductId());

    }


}
