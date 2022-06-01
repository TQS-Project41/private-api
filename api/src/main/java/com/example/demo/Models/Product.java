package com.example.demo.Models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import javax.persistence.Table;

@Table(name="product")
@Entity
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @NotNull(message = "name é obrigatório")
    private String name;

    @Column
    @NotNull(message = "name é obrigatório")
    private double price;

    @Column
    @NotNull(message = "Description é obrigatório")
    private String description;

    @Column
    @NotNull(message = "IsActive é obrigatório")
    private Boolean isActive;

    @OneToMany(mappedBy="product")
    private Set<ProductListItem> productListItems;
    
    @OneToMany(mappedBy="product")
    private Set<OrderProductItem> orderProductItem;

    @ManyToOne
    @JoinColumn(name="category_id", nullable=false)
    private Category category;

    
    public Product() {
        this.productListItems= new HashSet<>();
        this.orderProductItem= new HashSet<>();
    }


   
    
    public Product( String name,
             double price,
             String description,
            Boolean isActive, Set<ProductListItem> productListItems,
            Set<OrderProductItem> orderProductItem, Category category) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.isActive = isActive;
        this.productListItems = productListItems;
        this.orderProductItem = orderProductItem;
        this.category = category;
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public double getPrice() {
        return price;
    }


    public void setPrice(double price) {
        this.price = price;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public Boolean getIsActive() {
        return isActive;
    }


    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }


    
    
    public Set<OrderProductItem> getOrderProductItem() {
        return orderProductItem;
    }


    public void setOrderProductItem(Set<OrderProductItem> orderProductItem) {
        this.orderProductItem = orderProductItem;
    }


    public Set<ProductListItem> getProductListItems() {
        return productListItems;
    }


    public void setProductListItems(Set<ProductListItem> productListItems) {
        this.productListItems = productListItems;
    }


    public Category getCategory() {
        return category;
    }


    public void setCategory(Category category) {
        this.category = category;
    }


    @Override
    public String toString() {
        return "Product [description=" + description + ", id=" + id + ", isActive=" + isActive + ", name=" + name
                + ", price=" + price + ", category=" + category + "]";
    }
    
    
    
}
