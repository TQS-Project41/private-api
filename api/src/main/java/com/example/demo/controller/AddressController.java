package com.example.demo.controller;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Address;
import com.example.demo.models.Store;
import com.example.demo.models.User;
import com.example.demo.models.UserAddress;
import com.example.demo.security.AuthTokenFilter;
import com.example.demo.security.JwtUtils;
import com.example.demo.service.AddressService;
import com.example.demo.service.StoreService;
import com.example.demo.service.UserService;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
@RestController
@RequestMapping("addresses")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @Autowired
    private UserService userService;

    @GetMapping("")
    public ResponseEntity<List<Address>> getAddress() {
        Optional<User> user_opt = userService.getAuthenticatedUser();
        if (!user_opt.isPresent())  return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        User user = user_opt.get();
        List<Address> ret = addressService.getAllByUser(user);
        return new ResponseEntity<>(ret, HttpStatus.OK);    
    }

    @PostMapping("")
    public ResponseEntity<UserAddress> postAddress(@RequestParam String country,@RequestParam String zipcode,
            @RequestParam String city,@RequestParam String address) {
        Optional<User> user_opt = userService.getAuthenticatedUser();
        if (!user_opt.isPresent())  return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        User user = user_opt.get();
        Address ret = new Address(country, zipcode, city, address);
        UserAddress ret_final= addressService.createUserAddress(user, ret);

        return new ResponseEntity<>(ret_final, HttpStatus.CREATED);    
    }


    @GetMapping("{id}")
    public ResponseEntity<Address> getAddress(@PathVariable int id) {
        Optional<User> user = userService.getAuthenticatedUser();
        if (user.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        List<Address> addresses = addressService.getAllByUser(user.get());

        for (Address address : addresses) {
            if (address.getId() == id) {
                return ResponseEntity.status(HttpStatus.OK).body(address);
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    
}
