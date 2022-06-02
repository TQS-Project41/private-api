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
import com.example.demo.Models.Product;
import com.example.demo.Models.ProductList;
import com.example.demo.Models.Store;
import com.example.demo.Models.UserAddress;

import org.junit.jupiter.api.Test;
public class OrderListTest {
    @Test
    void testWhenCreateValidOrderListWithDefaultConstuctorThenReturnOrderList(){
        OrderList list = new OrderList();
        Store store = new Store();
        store.setName("puma");
        UserAddress userAddress = new UserAddress();
        list.setAddress(new Address("Portugal", "1903-221", "Aveiro", "Rua das Pombas", store, list, userAddress));
        list.setStore(store);
        list.setDeliveryId(1L);
        list.setId(1L);
        Set<OrderProductItem> orderProductItem=new HashSet<>();
        list.setOrderProductItem(orderProductItem);
        ProductList productList = new ProductList();
        productList.setId(1L);
        list.setProductList(productList);
        Set<OrderProductItem> productListItems=new HashSet<>();
        list.setProductListItems(productListItems);
        assertEquals("Portugal", list.getAddress().getCountry());
        assertEquals("puma", list.getStore().getName());
        assertEquals(1L, list.getDeliveryId());
        assertEquals(1L, list.getId());
        assertEquals(0, list.getProductListItems().size());
        assertEquals(1, list.getProductList().getId());
        assertEquals(0, list.getProductListItems().size());
    }

    @Test
    void testWhenCreateValidOrderListWithConstuctorThenReturnOrderList(){
        Store store = new Store();
        store.setName("puma");
        OrderList list_tmp= new OrderList();
        UserAddress userAddress = new UserAddress();
        Address address= new Address("Portugal", "1903-221", "Aveiro", "Rua das Pombas", store, list_tmp, userAddress);
        Set<OrderProductItem> orderProductItem=new HashSet<>();
        ProductList productList = new ProductList();
        productList.setId(1L);
        Set<OrderProductItem> productListItems=new HashSet<>();
        Long deliveryTimestamp=111111111111L;

        Long deliveryId=1L;
        OrderList list = new OrderList(productList, orderProductItem, address, store, deliveryId, deliveryTimestamp);
        list.setId(1L);
        list.setProductListItems(productListItems);
        assertEquals("Portugal", list.getAddress().getCountry());
        assertEquals("puma", list.getStore().getName());
        assertEquals(1L, list.getDeliveryId());
        assertEquals(1L, list.getId());
        assertEquals(111111111111L, list.getDeliveryTimestamp());
        assertEquals(0, list.getOrderProductItem().size());
        assertEquals(1, list.getProductList().getId());
        assertEquals(0, list.getProductListItems().size());
    }

    @Test
    void testWhenCreateValidOrderListWithConstuctorV2ThenReturnOrderList(){
        ProductList productList = new ProductList();
        productList.setId(1L);
        Set<OrderProductItem> productListItems=new HashSet<>();

        OrderList list = new OrderList(productList, productListItems);
        list.setId(1L);
        assertEquals(1L, list.getId());
        assertEquals(1, list.getProductList().getId());
        assertEquals(0, list.getProductListItems().size());
    }
}
