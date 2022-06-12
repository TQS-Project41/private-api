package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Product;
import com.example.demo.models.ProductListItem;
import com.example.demo.models.User;
import com.example.demo.service.CartListService;
import com.example.demo.service.ProductService;
import com.example.demo.service.UserService;

import org.springframework.security.core.Authentication;


@RestController
@RequestMapping("cart")
public class CartController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CartListService cartService;

    @Autowired
    private UserService userService;

    @PostMapping("")
    public ResponseEntity<ProductListItem> postCart( @RequestParam int product,@RequestParam int amount) {
        Optional<User> user_opt = userService.getAuthenticatedUser();
        if (!user_opt.isPresent())  return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        User user = user_opt.get();
        Optional<Product> p = productService.getById(product);
        if (!p.isPresent())  return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Product getProd = p.get();
        Optional<ProductListItem> ret = cartService.updateCartItem(user, getProd, amount);
        if (!ret.isPresent())  return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(ret.get(), HttpStatus.CREATED);    
    }

    @GetMapping("")
    public ResponseEntity<List<ProductListItem>> getCart() {
        Optional<User> user_opt = userService.getAuthenticatedUser();
        if (!user_opt.isPresent())  return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        User user = user_opt.get();
        List<ProductListItem> ret = cartService.getCurrentCartItems(user);
        return new ResponseEntity<>(ret, HttpStatus.OK);    
    }

}
