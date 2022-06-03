package com.example.demo.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import javax.persistence.Table;

@Table(name="productList")
@Entity
public class ProductList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(mappedBy="list")
    private Set<ProductListItem> productListItems;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    public ProductList() {
        this.productListItems = new HashSet<>();
    }
    
    public ProductList(User user) {
        this.productListItems = new HashSet<>();
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ProductList(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    

    public Set<ProductListItem> getProductListItems() {
        return productListItems;
    }

    @Override
    public String toString() {
        return "ProductList [id=" + id + "]";
    }

    
}
