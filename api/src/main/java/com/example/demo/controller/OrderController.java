package com.example.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Address;
import com.example.demo.models.OrderList;
import com.example.demo.models.Store;
import com.example.demo.models.User;
import com.example.demo.service.AddressService;
import com.example.demo.service.OrderListService;
import com.example.demo.service.StoreService;
import org.springframework.security.core.Authentication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("orders")
public class OrderController {

    @Autowired
    private OrderListService orderListService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private StoreService storeService;

    @GetMapping("")
    public Page<OrderList> getOrders(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return orderListService.findAll(user, Pageable.unpaged());
    }

    
    @PostMapping("")
    public ResponseEntity<OrderList> postOrders(Authentication authentication,@RequestParam int store
        ,@RequestParam int address ) {
        User user = (User) authentication.getPrincipal();
        Address getAddress = addressService.getById(address);
        if (getAddress == null ) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Store getStore = storeService.getById(store);
        if (getStore == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        //NEED HELP NESTE ENDPOINT
        //orderListService.createFromCart(user, address, store, deliveryId, deliveryTimestamp)
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @GetMapping("{id}")
    public ResponseEntity<OrderList> getByID( @PathVariable long id) {
        Optional<OrderList> ret = orderListService.findById(id);

        if (!ret.isPresent())  return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        OrderList ret_final = ret.get();
       
        return new ResponseEntity<>(ret_final,HttpStatus.OK);
    }

    /* 
    @DeleteMapping("{id}")
    public ResponseEntity<OrderList> deleteByID( @PathVariable long id) {

        Optional<OrderList> ret = orderListService

        if (!ret.isPresent())  return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        OrderList ret_final = ret.get();
       
        return new ResponseEntity<>(ret_final,HttpStatus.OK);
    }

    */
    
}
