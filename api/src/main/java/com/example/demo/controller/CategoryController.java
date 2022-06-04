package com.example.demo.controller;

import java.util.List;

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

import com.example.demo.models.Category;
import com.example.demo.service.CategoryService;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    
    @Autowired
    private CategoryService service;

    @GetMapping("category")
    public ResponseEntity<List<Category>> getCategories() {
        List<Category> ret = service.getAll();
        System.out.println(ret);
        return new ResponseEntity<>(ret, HttpStatus.OK);
    }

    @PostMapping("category")
    public ResponseEntity<Category> createCategory(@RequestParam String name) {
        List<Category> tmp = service.getAll();
        for (Category x : tmp){
            if (x.getName().equalsIgnoreCase(name)){
                return new ResponseEntity<>(new Category(), HttpStatus.BAD_REQUEST);
            }
        }
        Category ret = service.save(new Category(name, true));
        return new ResponseEntity<>(ret, HttpStatus.CREATED);
    }

    @PutMapping("category/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable int id,@RequestParam String name) {
        List<Category> tmp = service.getAll();
        boolean verify=true;
        Category ret =null;
        for (Category x : tmp){
            if (x.getId() ==    id ){
                x.setName("name");
                ret = x;
            }
            else if (x.getName().equalsIgnoreCase(name)){
                verify=false;
            }
        }
        if (ret  != null && verify ){
            ret.setName(name);
            service.save(ret);
            return new ResponseEntity<>(ret, HttpStatus.OK); 
        }

        return new ResponseEntity<>(new Category(), HttpStatus.NOT_FOUND);
    }


    @DeleteMapping("category/{id}")
    public ResponseEntity<Category> deleteCategory(@PathVariable int id) {
        List<Category> tmp = service.getAll();
        boolean verify=true;
        Category ret =null;
        for (Category x : tmp){
            if (x.getId() ==    id ){
                ret = x;
                ret.setActive(false);
                service.save(ret);
                return new ResponseEntity<>(ret, HttpStatus.OK);
            }
            
        }

        return new ResponseEntity<>(new Category(), HttpStatus.NOT_FOUND);
    }

}
