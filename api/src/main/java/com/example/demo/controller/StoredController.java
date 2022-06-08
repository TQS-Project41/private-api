package com.example.demo.controller;
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
import com.example.demo.models.SavedList;
import com.example.demo.models.Store;
import com.example.demo.models.User;
import com.example.demo.service.AddressService;
import com.example.demo.service.CartListService;
import com.example.demo.service.OrderListService;
import com.example.demo.service.ProductService;
import com.example.demo.service.SavedListService;
import com.example.demo.service.StoreService;
import org.springframework.security.core.Authentication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/order")
public class StoredController {
    
    @Autowired
    private ProductService service;

    @Autowired
    private StoreService storeService;


    @Autowired
    private SavedListService savedListService;


    @GetMapping("")
    public Page<SavedList> get(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return savedListService.findAll(user, Pageable.unpaged());

    }

    /* 
    @PostMapping("")
    public Page<SavedList> postStored(Authentication authentication,@RequestParam String name) {
        User user = (User) authentication.getPrincipal();
        
        // no ideia de qual lista ir buscar estou semi perido
        savedListService.save(new SavedList(productList, name));
        return savedListService.findAll(user, Pageable.unpaged());

    }

    */
    @GetMapping("/{id}")
    public ResponseEntity<SavedList> getByID( @PathVariable long id) {
        Optional<SavedList> ret = savedListService.findById(id);

        if (!ret.isPresent())  return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        SavedList ret_final = ret.get();
       
        return new ResponseEntity<>(ret_final,HttpStatus.OK);
    }
    /* 

    @PostMapping("/{id}")
    public ResponseEntity<SavedList> postByID( @PathVariable long id,
    @RequestParam int product,@RequestParam int amout) {

        Optional<SavedList> ret = savedListService.findById(id);

        if (!ret.isPresent())  return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        SavedList ret_final = ret.get();
        Optional<Product> productOptional = service.getById(product);
        if (!productOptional.isPresent())   return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Product product_final = productOptional.get();

        savedListService.save( new SavedList(productList, name))
       
        return new ResponseEntity<>(HttpStatus.OK);
    }
    */

    @DeleteMapping("/{id}")
    public ResponseEntity<SavedList> deleteByID( @PathVariable long id) {

        Optional<SavedList> ret = savedListService.findById(id);

        if (!ret.isPresent())  return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        SavedList ret_final = ret.get();

        savedListService.delete(ret_final);
       
        return new ResponseEntity<>(HttpStatus.OK);
    }

    
}
