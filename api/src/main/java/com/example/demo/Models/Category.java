package com.example.demo.Models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import javax.persistence.Table;

@Table(name="category")
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @NotNull(message = "name é obrigatório")
    private String name;


    @Column
    @NotNull(message = "IsActive é obrigatório")
    private boolean isActive;


    @OneToMany(mappedBy="category")
    private Set<Product> product;
    
    public Category() {
        this.product= new HashSet<>();
    }


    public Category(String name, boolean isActive) {
        this.name = name;
        this.isActive = isActive;
        this.product= new HashSet<>();
    }
    
    
    public Category( String name,
             boolean isActive, Set<Product> product) {
        this.name = name;
        this.isActive = isActive;
        this.product = product;
    }


    public Set<Product> getProduct() {
        return product;
    }


    public void setProduct(Set<Product> product) {
        this.product = product;
    }


    public long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public boolean getActive() {
        return isActive;
    }


    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }


    @Override
    public String toString() {
        return "Category [id=" + id + ", isActive=" + isActive + ", name=" + name + "]";
    }

    
    

}
