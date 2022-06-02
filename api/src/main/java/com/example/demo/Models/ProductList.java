package com.example.demo.Models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.NotNull;

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


    @OneToOne(mappedBy = "productList", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private CartList cartList;

    @OneToOne(mappedBy = "productList", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private SavedList savedList;

    @OneToOne(mappedBy = "productList", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private OrderList orderList;

    public CartList getCartList() {
        return cartList;
    }

    public void setCartList(CartList cartList) {
        this.cartList = cartList;
    }

    public ProductList() {
        this.productListItems = new HashSet<>();
    }
    
    public ProductList(Set<ProductListItem> productListItems, User user) {
        this.productListItems = productListItems;
        this.user = user;
    }

    public ProductList(Set<ProductListItem> productListItems, User user, CartList cartList, SavedList savedList,
            OrderList orderList) {
        this.productListItems = productListItems;
        this.user = user;
        this.cartList = cartList;
        this.savedList = savedList;
        this.orderList = orderList;
    }

    public SavedList getSavedList() {
        return savedList;
    }

    public void setSavedList(SavedList savedList) {
        this.savedList = savedList;
    }

   
    
    public OrderList getOrderList() {
        return orderList;
    }

    public void setOrderList(OrderList orderList) {
        this.orderList = orderList;
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

    public void setProductListItems(Set<ProductListItem> productListItems) {
        this.productListItems = productListItems;
    }

    @Override
    public String toString() {
        return "ProductList [id=" + id + "]";
    }

    
}
