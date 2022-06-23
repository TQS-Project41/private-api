package com.example.demo.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    
    public Category() {
    }

    public Category(String name, boolean isActive) {
        this.name = name;
        this.isActive = isActive;
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
