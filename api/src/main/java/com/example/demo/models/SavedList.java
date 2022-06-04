package com.example.demo.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Value;

@Table(name="savedList")
@Entity
public class SavedList {
    @Id
    @Column(name = "productList_id")
    private Long id;


    @OneToOne
    @MapsId
    @JoinColumn(name = "productList_id")
    private ProductList productList;

    @Column
    @Value("")
    private String name;

    public SavedList() {
    }


    


    public SavedList(ProductList productList, String name) {
        this.productList = productList;
        this.name = name;
    }





    public ProductList getProductList() {
        return productList;
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


    public void setProductList(ProductList productList) {
        this.productList = productList;
    }
}
