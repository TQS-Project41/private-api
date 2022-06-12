package com.example.demo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.demo.models.Address;
import com.example.demo.models.User;
import com.example.demo.models.UserAddress;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.UserAddressRepository;

import org.junit.jupiter.api.Test;
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
  @Mock(lenient = true)
  private UserAddressRepository uRepository;
  @InjectMocks
  private AddressService service;

  @BeforeEach
  public void setUp() {
    Address address1 = new Address("Portugal", "1111-111", "Aveiro", "DETI");
    Address address2 = new Address("Portugal", "1121-111", "Setúbal", "Casa");
    Address address3 = new Address("Portugal", "1321-111", "Entroncamento", "Farmácia Nova");
    address1.setId(1l);
    User user = new User("alex20002011", "Alex", "xxx", LocalDate.of(2000, 05, 20), "964546324", false, false);
    UserAddress ua1= new UserAddress(user, address1);
    UserAddress ua2= new UserAddress(user, address2);
    UserAddress ua3= new UserAddress(user, address3);

    Mockito.when(repository.findById(1L)).thenReturn(Optional.of(address1));
    Mockito.when(repository.findById(-1L)).thenReturn(Optional.empty());
    Mockito.when(repository.findAll()).thenReturn(Arrays.asList(address1, address2, address3));
    Mockito.when(uRepository.findByUser(any())).thenReturn(Arrays.asList(ua1, ua2, ua3));


  }
  @Test
  public void whenSearchAddressByInvalidId_thenAddressShouldNotBeFound() {
    Address address = service.getById(-1L);
    assertThat(address).isNull();
     Mockito.verify(repository, VerificationModeFactory.times(1)).findById(-1L);
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
  @Test
  public void whenSaveAddress_thenReturnAddress() {
    Address address = new Address();
    address.setAddress("Rua da Estia");
    Mockito.when(repository.save(any())).thenReturn(address);
    assertThat(service.save(address)).isEqualTo(address);
    Mockito.verify(repository,VerificationModeFactory.times(1)).save(address);
  }

  @Test
  public void whenGetAllAddressOfUser_thenReturnListOfAddress() {
    User user = new User("alex20002011", "Alex", "xxx", LocalDate.of(2000, 05, 20), "964546324", false, false);
    List<Address> ret = service.getAllByUser(user);
    assertEquals(3,ret.size());
    assertEquals("Aveiro",ret.get(0).getCity());
    assertEquals("Setúbal",ret.get(1).getCity());
    assertEquals("Entroncamento",ret.get(2).getCity());

    Mockito.verify(uRepository,VerificationModeFactory.times(1)).findByUser(user);
  }

  @Test
  public void whenSaveAddressOfUser_thenReturnAddressofUser() {
    User user = new User("alex20002011", "Alex", "xxx", LocalDate.of(2000, 05, 20), "964546324", false, false);
    Address address1 = new Address("Portugal", "1111-111", "Aveiro", "DETI");
    UserAddress ua1= new UserAddress(user, address1);
  
    Mockito.when(uRepository.save(any())).thenReturn(ua1);

    assertThat(service.createUserAddress(user,address1)).isEqualTo(ua1);
    Mockito.verify(uRepository,VerificationModeFactory.times(1)).save(any());

  }


}
