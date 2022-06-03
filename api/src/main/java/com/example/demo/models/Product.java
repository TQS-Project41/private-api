package com.example.demo.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

    @ManyToOne
    @JoinColumn(name="category_id", nullable=false)
    private Category category;

    
    public Product() {
    }
    
    public Product(String name, float price, String description, Boolean isActive, Category category) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.isActive = isActive;
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
