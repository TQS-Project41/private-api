package com.example.demo.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class LoginDto {

  @NotEmpty
  @Email
  private String email;

  @NotEmpty
  private String password;



  public String getEmail() {
    return this.email;
  }

  public String getPassword() {
    return this.password;
  }

 
  
}
