package com.example.demo.model;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import com.example.demo.models.Address;
import com.example.demo.models.OrderList;
import com.example.demo.models.ProductList;
import com.example.demo.models.Store;

import org.junit.jupiter.api.Test;
public class OrderListTest {
    @Test
    void testWhenCreateValidOrderListWithDefaultConstuctorThenReturnOrderList(){
        Address address = new Address("Portugal", "1903-221", "Aveiro", "Rua das Pombas");
        Store store = new Store("puma", address);

        ProductList productList = new ProductList();
        productList.setId(1L);

        OrderList list = new OrderList();
        list.setId(1L);
        list.setStore(store);
        list.setDeliveryId(1L);
        list.setProductList(productList);
        list.setAddress(address);

        assertEquals("Portugal", list.getAddress().getCountry());
        assertEquals("puma", list.getStore().getName());
        assertEquals(1L, list.getDeliveryId());
        assertEquals(1L, list.getId());
        assertEquals(1, list.getProductList().getId());
    }

    @Test
    void testWhenCreateValidOrderListWithConstuctorThenReturnOrderList(){
        Address address = new Address("Portugal", "1903-221", "Aveiro", "Rua das Pombas");
        Store store = new Store("puma", address);

        LocalDateTime deliveryTimestamp = LocalDateTime.of(2022, 6, 22, 10, 15);
        Long deliveryId = 1L;

        ProductList productList = new ProductList();
        productList.setId(1L);

        OrderList list = new OrderList(productList, address, store, deliveryId, deliveryTimestamp);
        list.setId(1L);

        assertEquals("Portugal", list.getAddress().getCountry());
        assertEquals("puma", list.getStore().getName());
        assertEquals(1L, list.getDeliveryId());
        assertEquals(1L, list.getId());
        assertEquals(deliveryTimestamp, list.getDeliveryTimestamp());
        assertEquals(1, list.getProductList().getId());
    }
}
