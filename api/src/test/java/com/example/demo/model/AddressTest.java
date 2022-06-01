package com.example.demo.model;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import com.example.demo.Models.Address;
import com.example.demo.Models.UserAddress;

import org.junit.jupiter.api.Test;
public class AddressTest {
    
    @Test
    void testWhenCreateValidAddressWithDefaultConstuctorThenReturnAddress(){
        Address x = new Address();

        x.setAddress("Rua das Pombas");
        x.setCity("Aveiro");
        x.setCountry("Portugal");
        x.setOrderList(null);
        x.setId(1);
        x.setStore(null);
        x.setZipcode("1204-322");
        Set<UserAddress> u_adress= new HashSet();
        x.setUserAddress(u_adress);

        assertEquals("Rua das Pombas", x.getAddress());
        assertEquals("Aveiro", x.getCity());
        assertEquals("Portugal", x.getCountry());
        assertEquals("1204-322", x.getZipcode());
        assertEquals(null, x.getOrderList());
        assertEquals(1, x.getId());
        assertEquals(null, x.getStore());
        assertEquals(0, x.getUserAddress().size());

    }
    @Test
    void testWhenCreateValidAddressWithConstuctorWithFullParametersThenReturnAddress(){
        Set<UserAddress> u_adress= new HashSet();
        Address x = new Address("Portugal", "1204-322", "Aveiro", "Rua das Pombas", null, null, u_adress);


        assertEquals("Rua das Pombas", x.getAddress());
        assertEquals("Aveiro", x.getCity());
        assertEquals("Portugal", x.getCountry());
        assertEquals("1204-322", x.getZipcode());
        assertEquals(null, x.getOrderList());
        assertEquals(null, x.getStore());
        assertEquals(0, x.getUserAddress().size());

    }
}
