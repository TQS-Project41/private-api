package com.example.demo.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UserDto {

  private String email;

  private String name;
  
  private String password;

  private String birthday;

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
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    return LocalDate.parse(this.birthday, formatter);
  }

  public String getPhoneNumber() {
    return this.phoneNumber;
  }
  
}
