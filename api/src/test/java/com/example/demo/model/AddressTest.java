package com.example.demo.model;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.demo.models.Address;

import org.junit.jupiter.api.Test;
public class AddressTest {
    
    @Test
    void testWhenCreateValidAddressWithDefaultConstuctorThenReturnAddress(){
        Address x = new Address();

        x.setAddress("Rua das Pombas");
        x.setCity("Aveiro");
        x.setCountry("Portugal");
        x.setId(1);
        x.setZipcode("1204-322");

        assertEquals("Rua das Pombas", x.getAddress());
        assertEquals("Aveiro", x.getCity());
        assertEquals("Portugal", x.getCountry());
        assertEquals("1204-322", x.getZipcode());
        assertEquals(1, x.getId());
    }

    @Test
    void testWhenCreateValidAddressWithConstuctorWithFullParametersThenReturnAddress(){
        Address x = new Address("Portugal", "1204-322", "Aveiro", "Rua das Pombas");

        assertEquals("Rua das Pombas", x.getAddress());
        assertEquals("Aveiro", x.getCity());
        assertEquals("Portugal", x.getCountry());
        assertEquals("1204-322", x.getZipcode());
    }
}
