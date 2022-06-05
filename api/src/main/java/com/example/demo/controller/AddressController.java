package com.example.demo.controller;
import java.util.List;


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
import com.example.demo.service.AddressService;
import com.example.demo.service.StoreService;

@RestController
@RequestMapping("/addresses")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @GetMapping("address/{id}")
    public ResponseEntity<Address> getAddress(@PathVariable int id) {
        Address ret = addressService.getById(id);
        if (ret != null) return new ResponseEntity<>(ret, HttpStatus.OK);
        else return new ResponseEntity<>(new Address(), HttpStatus.BAD_REQUEST);
    }
    
}
