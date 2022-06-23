package com.example.demo.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class UserDto {

  @NotEmpty
  @Email
  private String email;

  @NotEmpty
  private String name;
  @NotEmpty
  private String password;
  @NotEmpty
  private String birthday;
  @NotEmpty
  private String phoneNumber;

  public String getEmail() {
    return this.email;
  }

  public String getName() {
    return this.name;
  }

  public String getPassword() {
    return this.password;
  }

  public LocalDate getBirthday() {
    if (this.birthday ==null) return null;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    return LocalDate.parse(this.birthday, formatter);
  }

  public String getPhoneNumber() {
    return this.phoneNumber;
  }
  
}
