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
@RequestMapping("stores")
public class StoreController {
    
    @Autowired
    private StoreService service;

    @Autowired
    private AddressService addressService;

    @GetMapping("")
    public ResponseEntity<List<Store>> getStores() {
        List<Store> ret = service.getAll(); 
        return new ResponseEntity<>(ret, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Store> saveStore(@RequestParam String name,
    @RequestParam String country, @RequestParam String zipcode, @RequestParam String city,
    @RequestParam String address) {
        Address a = addressService.save(new Address(country, zipcode, city, address));
        Store ret = service.save(new Store(name, a));
        return new ResponseEntity<>(ret, HttpStatus.CREATED);
    }
    
    @GetMapping("{id}")
    public ResponseEntity<Store> getStory(@PathVariable int id) {
        Store ret = service.getById(id);
        if (ret != null) return new ResponseEntity<>(ret, HttpStatus.OK);
        else return new ResponseEntity<>(new Store(), HttpStatus.BAD_REQUEST);

    }
    @PutMapping("{id}")
    public ResponseEntity<Store> updateStore(@PathVariable int id,@RequestParam String name ) {
        Store ret = service.getById(id);
        if (ret != null) {
            ret.setName(name);
            service.save(ret);
            return new ResponseEntity<>(ret, HttpStatus.OK);
        }
        else return new ResponseEntity<>(new Store(), HttpStatus.BAD_REQUEST);

    }
}
