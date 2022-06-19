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
import com.example.demo.models.ProductList;
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
import com.example.demo.service.UserService;

import org.springframework.security.core.Authentication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("stored_lists")
public class StoredController {
    
    @Autowired
    private ProductService service;

    @Autowired
    private SavedListService savedListService;

    @Autowired
    private UserService userService;

    @GetMapping("")
    public Page<SavedList> getOrders() {
        Optional<User> user_opt = userService.getAuthenticatedUser();
        if (!user_opt.isPresent())  return Page.empty();
        User user = user_opt.get();
        return savedListService.findAll(user, Pageable.unpaged());

    }

    
    @PostMapping("")
    public ResponseEntity<SavedList> postStored(@RequestParam String name) {
        Optional<User> user_opt = userService.getAuthenticatedUser();
        if (!user_opt.isPresent())  return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        User user = user_opt.get();
        ProductList productList = new ProductList(user);
        SavedList x = savedListService.save(new SavedList(productList, name));
        return new ResponseEntity<>(x,HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<SavedList> getStoredListByID( @PathVariable long id) {
        Optional<SavedList> ret = savedListService.findById(id);

        if (!ret.isPresent())  return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        SavedList ret_final = ret.get();

        Optional<User> user_opt = userService.getAuthenticatedUser();
        if (!user_opt.isPresent())  return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        User user = user_opt.get();
        if (user.getId() == ret_final.getProductList().getUser().getId() || user.getAdmin() || user.getStaff()){
            return new ResponseEntity<>(ret_final,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        
    }
    

    @PostMapping("{id}")
    public ResponseEntity<ProductListItem> postByID( @PathVariable long id,
    @RequestParam int product,@RequestParam int amount) {
        Optional<SavedList> ret = savedListService.findById(id);
        if (!ret.isPresent())  return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        SavedList ret_final = ret.get();
        Optional<Product> productOptional = service.getById(product);
        if (!productOptional.isPresent())   return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Optional<User> user_opt = userService.getAuthenticatedUser();
        if (!user_opt.isPresent())  return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        User user = user_opt.get();
        if (user.getId() == ret_final.getProductList().getUser().getId() || user.getAdmin() || user.getStaff()){
            Optional<ProductListItem> product_final = savedListService.updateListItem(ret_final, productOptional.get(), amount);
            if (!product_final.isPresent())   return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(product_final.get(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
    

    @DeleteMapping("{id}")
    public ResponseEntity<SavedList> deleteByID( @PathVariable long id) {

        Optional<SavedList> ret = savedListService.findById(id);

        if (!ret.isPresent())  return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        SavedList ret_final = ret.get();

       
        Optional<User> user_opt = userService.getAuthenticatedUser();
        if (!user_opt.isPresent())  return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        User user = user_opt.get();
        if (user.getId() == ret_final.getProductList().getUser().getId() || user.getAdmin() || user.getStaff()){
            savedListService.delete(ret_final);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }


    @GetMapping("{id}/products")
    public ResponseEntity<List<ProductListItem>> getProductsListByID( @PathVariable long id) {
        Optional<SavedList> ret = savedListService.findById(id);

        if (!ret.isPresent())  return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        SavedList ret_final = ret.get();

        Optional<User> user_opt = userService.getAuthenticatedUser();
        if (!user_opt.isPresent())  return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        User user = user_opt.get();
        if (user.getId() == ret_final.getProductList().getUser().getId() || user.getAdmin() || user.getStaff()){
            return new ResponseEntity<>(savedListService.getAllProducts(id), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        
    }

    
}
