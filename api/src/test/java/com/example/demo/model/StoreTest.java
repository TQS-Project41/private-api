package com.example.demo.model;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import com.example.demo.Models.Address;
import com.example.demo.Models.Category;
import com.example.demo.Models.OrderList;
import com.example.demo.Models.OrderProductItem;
import com.example.demo.Models.Product;
import com.example.demo.Models.ProductList;
import com.example.demo.Models.ProductListItem;
import com.example.demo.Models.SavedList;
import com.example.demo.Models.Store;

import org.junit.jupiter.api.Test;
public class StoreTest {


    @Test
    void testWhenCreateValidStoreDefaultConstructorThenReturnStore(){
        Address address=  new Address();
        address.setCity("Aveiro");
        Store store= new Store();
        store.setId(1L);
        store.setAddress(address);
        store.setName("Continente");
        assertEquals(1L, store.getId());
        assertEquals("Continente", store.getName());
            assertEquals(0, store.getOrderList().size());
    }
    

	@Test
    void testWhenCreateValidStoreConstructorThenReturnStore(){
        Set<OrderList> orderList = new HashSet<>();
        Address address=  new Address();
        address.setCity("Aveiro");
        Store store= new Store("Continente",address, orderList);
        store.setId(1L);
        assertEquals(1L, store.getId());
        assertEquals("Continente", store.getName());
            assertEquals(0, store.getOrderList().size());
    }
    
}
