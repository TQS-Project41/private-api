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
import com.example.demo.models.Product;
import com.example.demo.models.ProductListItem;
import com.example.demo.models.Store;
import com.example.demo.models.User;
import com.example.demo.service.AddressService;
import com.example.demo.service.CartListService;
import com.example.demo.service.ProductService;
import com.example.demo.service.StoreService;
import org.springframework.security.core.Authentication;


@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CartListService cartService;

    @PostMapping("")
    public ResponseEntity<ProductListItem> postCart(Authentication authentication, @RequestParam int product,@RequestParam int amount) {
        User user = (User) authentication.getPrincipal();
        Optional<Product> p = productService.getById(product);
        if (!p.isPresent())  return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Product getProd = p.get();
        Optional<ProductListItem> ret = cartService.updateCartItem(user, getProd, amount);
        return new ResponseEntity<>(ret.get(), HttpStatus.CREATED);    
    }

    @GetMapping("")
    public ResponseEntity<List<ProductListItem>> getCart(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        List<ProductListItem> ret = cartService.getCurrentCartItems(user);
        return new ResponseEntity<>(ret, HttpStatus.OK);    
    }

}
