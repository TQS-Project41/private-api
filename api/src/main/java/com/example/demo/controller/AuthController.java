package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.UserDto;
import com.example.demo.models.User;
import com.example.demo.security.JwtUtils;
import com.example.demo.service.UserService;

@RestController
public class AuthController {

  @Autowired
  private UserService userService;

  @Autowired 
  private JwtUtils jwtUtils;

  @Value("${coviran.app.jwtSecret}")
  private String jwtSecret;

  @Value("${coviran.app.jwtExpirationMs}")
  private int jwtExpirationMs;

  @PostMapping("register")
  public ResponseEntity<User> register(@RequestBody UserDto user) {
    User registered = userService.save(new User(user.getEmail(), user.getName(), user.getPassword(), user.getBirthday(), user.getPhoneNumber(), false, false));
    return new ResponseEntity<>(registered, HttpStatus.CREATED);
  }

  @PostMapping("login")
  public ResponseEntity<Map<String, String>> login(@RequestParam String email, @RequestParam String password) {
    Optional<User> user = userService.getByEmailAndPassword(email, password);

    if (user.isPresent()) {
      Map<String, String> response = new HashMap<>();
      response.put("token", jwtUtils.generateJwtToken(user.get().getId()));

      return new ResponseEntity<>(response, HttpStatus.OK);
    }

    return new ResponseEntity<>(HttpStatus.NOT_FOUND);

  }
  
}
