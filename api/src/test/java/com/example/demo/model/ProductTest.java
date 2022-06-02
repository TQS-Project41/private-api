package com.example.demo.model;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import com.example.demo.Models.Category;
import com.example.demo.Models.OrderProductItem;
import com.example.demo.Models.Product;
import com.example.demo.Models.ProductListItem;

import org.junit.jupiter.api.Test;
public class ProductTest {


    @Test
    void testWhenCreateValidProductConstuctorDefaultThenReturnProduct(){
        Product product = new Product();
        product.setId(1L);
        product.setDescription("leve");
        product.setName("Pilhas");
        product.setPrice(5.1);
        product.setIsActive(true);
        product.setCategory(new Category());
        Set<OrderProductItem> orderProductItem = new HashSet();
        product.setOrderProductItem(orderProductItem);
        Set<ProductListItem> productListItems= new HashSet();
        product.setProductListItems(productListItems);
        assertEquals(1L, product.getId());
        assertEquals("leve", product.getDescription());
        assertEquals("Pilhas", product.getName());
        assertEquals(true, product.getIsActive());
        assertEquals(5.1, product.getPrice());
        assertEquals(0, product.getCategory().getId());
        assertEquals(0, product.getOrderProductItem().size());
        assertEquals(0, product.getCategory().getProduct().size());
    }


    @Test
    void testWhenCreateValidProductConstuctorThenReturnProduct(){
        Set<OrderProductItem> orderProductItem = new HashSet();
        Set<ProductListItem> productListItems= new HashSet();
        Product product = new Product("Pilhas", 5.1, "leve", true, productListItems, orderProductItem, new Category());
        product.setId(1L);
        assertEquals(1L, product.getId());
        assertEquals("leve", product.getDescription());
        assertEquals("Pilhas", product.getName());
        assertEquals(5.1, product.getPrice());
        assertEquals(true, product.getIsActive());
        assertEquals(0, product.getCategory().getId());
        assertEquals(0, product.getOrderProductItem().size());
        assertEquals(0, product.getCategory().getProduct().size());
    }
}
