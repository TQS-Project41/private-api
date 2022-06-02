package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Optional;

import com.example.demo.Models.Address;
import com.example.demo.Repository.AddressRepository;
import com.example.demo.Service.AddressService;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class AddressServiceTest {

  @Mock(lenient = true)
  private AddressRepository repository;

  @InjectMocks
  private AddressService service;

  @BeforeEach
  public void setUp() {
    Address address1 = new Address("Portugal", "1111-111", "Aveiro", "DETI");
    Address address2 = new Address("Portugal", "1121-111", "Setúbal", "Casa");
    Address address3 = new Address("Portugal", "1321-111", "Entroncamento", "Farmácia Nova");

    Mockito.when(repository.findById(1L)).thenReturn(Optional.of(address1));
    Mockito.when(repository.findAll()).thenReturn(Arrays.asList(address1, address2, address3));
  }

  @Test
  public void whenSearchAddressById_thenReturnsAddress() {
    Address address = service.getById(1L);

    assertEquals("Portugal", address.getCountry());
    assertEquals("1111-111", address.getZipcode());
    assertEquals("Aveiro", address.getCity());
    assertEquals("DETI", address.getAddress());

    Mockito.verify(repository, VerificationModeFactory.times(1)).findById(1L);
  }
  
}
