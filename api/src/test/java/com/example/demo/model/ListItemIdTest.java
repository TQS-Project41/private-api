package com.example.demo.model;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.demo.models.ListItemId;

import org.junit.jupiter.api.Test;
public class ListItemIdTest {
    
    @Test
    void testWhenCreateValidProductListItemIdDefaultConstructorThenReturnProductListItemId(){
        ListItemId p = new ListItemId();
        p.setListId(1L);
        p.setProductId(1L);

        assertEquals(1L, p.getListId());
        assertEquals(1L, p.getProductId());

    }

    @Test
    void testWhenCreateValidProductListItemIdConstructorThenReturnProductListItemId(){
        ListItemId p = new ListItemId(1L,1L);
    
        assertEquals(1L, p.getListId());
        assertEquals(1L, p.getProductId());

    }


}
