package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import com.example.demo.models.Category;
import com.example.demo.models.Product;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ProductService;

@RestController
@RequestMapping("products")
public class ProductController {

    @Autowired
    private ProductService service;
    

    @Autowired
    private CategoryService catService;


    @GetMapping("")
    public Page<Product> getProducts(
        @RequestParam(name = "category", required = false) Long categoryId,
        @RequestParam(name = "min_price", required = false, defaultValue = "0") Float minPrice,
        @RequestParam(name = "max_price", required = false) Float maxPrice,
        @RequestParam(name = "query", required = false, defaultValue = "") String query,
        Pageable pageable
    ) {
        return service.getAll(categoryId, query, minPrice, maxPrice, pageable);
    }

    @PostMapping("")
    public ResponseEntity<Product> createProducts(@RequestParam String name,@RequestParam String description
            , @RequestParam float price, @RequestParam int category ) {
        List<Category> a = catService.getAll();
        for (Category cat : a){
            if (cat.getId() == category){
                Product product = new Product(name, price, description, true, cat);
                Product ret =service.save(product);
                return new ResponseEntity<>(ret, HttpStatus.CREATED);

            }
        }
        return new ResponseEntity<>(new Product(), HttpStatus.NOT_FOUND);

    }

    @GetMapping("{id}")
    public ResponseEntity<Product> getProduct(@PathVariable int id ) {
        Product x = service.getById(id).orElse(null);
        if (x == null){
            return new ResponseEntity<>(new Product(), HttpStatus.NOT_FOUND);

        }
        return new ResponseEntity<>(x, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable int id ) {
        Product x = service.getById(id).orElse(null);
        if (x == null){
            return new ResponseEntity<>(new Product(), HttpStatus.NOT_FOUND);

        }
        x.setIsActive(false);
        service.save(x);
        return new ResponseEntity<>(x, HttpStatus.OK);
    }
    
    @PutMapping("{id}")
    public ResponseEntity<Product> putProduct(@PathVariable int id,
    @RequestParam(required = false)  String name,@RequestParam(required = false)  String description
    , @RequestParam (defaultValue="-1",required = false) float price, @RequestParam(defaultValue = "-1",required = false) int category ) {
        // Optional<Product> productOptional = service.getById(id);
        
        // if (!productOptional.isPresent()) 
        //     return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        // Product product = productOptional.get();

        // if (name != null) product.setName(name);
        // if (description != null) product.setDescription(description);
        // if (price >= 0) product.setPrice(price);

        // if (category > 0) {
        //     Optional<Category> categoryOptional = catService.getById(category);

        //     if (!productOptional.isPresent())
        //         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        // }
        
        
        if (category != -1){
          List<Category> a = catService.getAll();
        
        for (Category cat : a){
            if (cat.getId() == category){
                Product x = service.getById(id).orElse(null);
            if (x == null){
                return new ResponseEntity<>(new Product(), HttpStatus.NOT_FOUND);

            }
            if (name != null){
                x.setName(name);
            }
            if (description != null){
                x.setDescription(description);
            }
            if (price != -1f){
                x.setPrice(price);
            }
            x.setCategory(cat);
            service.save(x);
            return new ResponseEntity<>(x, HttpStatus.OK);

            }
        }
        return new ResponseEntity<>(new Product(), HttpStatus.NOT_FOUND);  
        }
        else{
            Product x = service.getById(id).orElse(null);
            if (x == null){
                return new ResponseEntity<>(new Product(), HttpStatus.NOT_FOUND);

            }
            if (name != null){
                x.setName(name);
            }
            if (description != null){
                x.setDescription(description);
            }
            if (price != -1f){
                x.setPrice(price);
            }
            service.save(x);
            return new ResponseEntity<>(x, HttpStatus.OK);
        }

        
    }
}
