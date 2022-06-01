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
import com.example.demo.Models.ProductListItemId;
import com.example.demo.Models.SavedList;
import com.example.demo.Models.User;

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
