package com.example.demo.model;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.demo.models.Address;
import com.example.demo.models.Store;

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
    }
    

	@Test
    void testWhenCreateValidStoreConstructorThenReturnStore(){
        Address address=  new Address();
        address.setCity("Aveiro");
        Store store= new Store("Continente", address);
        store.setId(1L);
        assertEquals(1L, store.getId());
        assertEquals("Continente", store.getName());
    }
    
}
