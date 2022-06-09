package com.example.demo.controller;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Address;
import com.example.demo.models.OrderList;
import com.example.demo.models.Product;
import com.example.demo.models.ProductListItem;
import com.example.demo.models.Store;
import com.example.demo.models.User;
import com.example.demo.service.AddressService;
import com.example.demo.service.CartListService;
import com.example.demo.service.OrderListService;
import com.example.demo.service.ProductService;
import com.example.demo.service.StoreService;
import org.springframework.security.core.Authentication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/order")
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
        ,@RequestParam int address ,@RequestParam String deliveryTimestamp) {
        User user = (User) authentication.getPrincipal();
        Address getAddress = addressService.getById(address);
        if (getAddress == null ) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Store getStore = storeService.getById(store);
        if (getStore == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        //NEED HELP NESTE ENDPOINT
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        orderListService.createFromCart(user, getAddress, getStore, 0L, LocalDateTime.parse(deliveryTimestamp, formatter));
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<OrderList> getByID( @PathVariable long id) {
        Optional<OrderList> ret = orderListService.findById(id);

        if (!ret.isPresent())  return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        OrderList ret_final = ret.get();
       
        return new ResponseEntity<>(ret_final,HttpStatus.OK);
    }

    /* PEDIR À GENERICA
    @DeleteMapping("/{id}")
    public ResponseEntity<OrderList> deleteByID( @PathVariable long id) {

        Optional<OrderList> ret = orderListService

        if (!ret.isPresent())  return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        OrderList ret_final = ret.get();
       
        return new ResponseEntity<>(ret_final,HttpStatus.OK);
    }

    */
    
}
