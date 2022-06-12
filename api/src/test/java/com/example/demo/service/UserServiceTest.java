package com.example.demo.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import com.example.demo.models.User;
import com.example.demo.repository.UserRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
  
  @Mock(lenient = true)
  private UserRepository repository;

  @InjectMocks
  private UserService service;

  @Test
  public void whenSavingValidUser_thenReturnsUserWithId() {
    User user = new User();

    User savedUser = new User();
    savedUser.setId(1L);

    Mockito.when(repository.save(user)).thenReturn(savedUser);

    assertThat(user.getId()).isZero();
    assertThat(service.save(user).getId()).isNotZero();

    Mockito.verify(repository, VerificationModeFactory.times(1)).save(user);
  }

  @Test
  public void whenGettingProductById_thenReturnValidOptional() {
    Mockito.when(repository.findById(1L)).thenReturn(Optional.of(new User()));

    assertThat(service.getById(1L)).isPresent();
    assertThat(service.getById(10L)).isNotPresent();

    Mockito.verify(repository, VerificationModeFactory.times(1)).findById(1L);
    Mockito.verify(repository, VerificationModeFactory.times(1)).findById(10L);
  }

  @Test
  public void whenGettingProductByUsernameAndPassword_thenReturnValidOptional() {
    Mockito.when(repository.findById(1L)).thenReturn(Optional.of(new User()));

    assertThat(service.getById(1L)).isPresent();
    assertThat(service.getById(10L)).isNotPresent();

    Mockito.verify(repository, VerificationModeFactory.times(1)).findById(1L);
    Mockito.verify(repository, VerificationModeFactory.times(1)).findById(10L);
  }

  @Test
  public void whenGettingAuthenticatedUser_ThenReturnsValidOptional() {
    assertThat(service.getAuthenticatedUser().getClass()).isEqualTo(Optional.class);
  }

}
